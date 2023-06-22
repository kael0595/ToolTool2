package com.ll.sbb.Email;

import com.ll.sbb.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public void updatePassword(String str, String userEmail) {
        String pw = passwordEncoder.encode(str);
        int id = userRepository.findUserById(userEmail).getId();
        userRepository.updateUserPassword(id, pw);
    }
}
