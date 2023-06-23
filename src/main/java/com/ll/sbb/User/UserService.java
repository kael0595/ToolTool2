package com.ll.sbb.User;

import com.ll.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SiteUser create(String username, String password, String email, String nickname, int mailKey, UserRole role) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setMailKey(mailKey);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreateDate(LocalDate.now());
        this.userRepository.save(user);
        return user;
    }

    public void updateMailKey(int mailKey, String email, Long id) {
        userRepository.updateMailKey(mailKey, email, id);
    }

    public void updateMailAuth(String email, int mailKey) {
        int updatedRows = userRepository.updateMailAuth(email, mailKey);
        if (updatedRows > 0) {
            System.out.println("Mail auth updated successfully.");
        } else {
            System.out.println("Failed to update mail auth.");
        }
    }

    public long countByEmailAndMailAuth(String email) {
        return userRepository.countByEmailAndMailAuth(email, true);
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }


    public boolean authenticateUser(String username, String password) {
        Optional<SiteUser> siteUserOptional = userRepository.findByusername(username);
        if (siteUserOptional.isPresent()) {
            SiteUser siteUser = siteUserOptional.get();
            return passwordEncoder.matches(password, siteUser.getPassword());
        }
        return false;
    }

    public SiteUser getUserByEmail(String email) {
        Optional<SiteUser> siteUserOptional = this.userRepository.findByEmail(email);
        if (siteUserOptional.isPresent()) {
            return siteUserOptional.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public void emailConfirm(String email, int mailKey) throws Exception {
        SiteUser user = this.getUserByEmail(email);

        if (user != null && user.getMailKey() == mailKey) {
            updateMailAuth(email, mailKey);
        } else {
            throw new Exception("유효하지 않은 이메일 또는 메일 키입니다.");
        }
    }

    public SiteUser getUserByEmailAndUsername(String email, String username) {
        Optional<SiteUser> siteUserOptional = userRepository.findUserByEmailAndUsername(email, username);
        if (siteUserOptional.isPresent()) {
            return siteUserOptional.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public String generateTempPassword() {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public boolean userEmailCheck(String userEmail, String userName) {

        SiteUser user = userRepository.findUserById(userEmail);
        if (user != null && user.getUsername().equals(userName)) {
            return true;
        } else {
            return false;
        }
    }

    public void updatePassword(String str, String userEmail) {
        String pw = passwordEncoder.encode(str);
        int id = userRepository.findUserById(userEmail).getId();
        userRepository.updateUserPassword(id, pw);
    }

    public SiteUser getUserByUsername(String username) {
        Optional<SiteUser> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    public SiteUser saveUser(SiteUser user) {
        return userRepository.save(user);
    }
}