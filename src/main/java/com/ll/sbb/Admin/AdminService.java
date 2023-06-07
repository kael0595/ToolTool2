package com.ll.sbb.Admin;

import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private UserRepository userRepository;

    public SiteUser createAdmin(String username, String password) {
        SiteUser admin = new SiteUser();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setAdmin(true);
        return userRepository.save(admin);
    }
}
