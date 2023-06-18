package com.ll.sbb.User;

import com.ll.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String password, String email, String nickname) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public void updateMailKey(String mailKey, String email, Long id) {
        userRepository.updateMailKey(mailKey, email, id);
    }

    public void updateMailAuth(String email, String mailKey) {
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

    public void emailConfirm(String email, String mailKey) throws Exception {
        SiteUser user = getUserByEmail(email);

        if (user != null && user.getMailKey().equals(mailKey)) {
            updateMailAuth(email, mailKey);
        } else {
            throw new Exception("유효하지 않은 이메일 또는 메일 키입니다.");
        }
    }

}