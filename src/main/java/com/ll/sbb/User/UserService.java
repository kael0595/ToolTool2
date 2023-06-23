package com.ll.sbb.User;

import com.ll.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

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

    public SiteUser getPwByEmailAndUserName(String email, String username) {
        Optional<SiteUser> siteUserOptional = this.userRepository.findPwByEmailAndUsername(email, username);
        if (siteUserOptional.isPresent()) {
            return siteUserOptional.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }

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


}