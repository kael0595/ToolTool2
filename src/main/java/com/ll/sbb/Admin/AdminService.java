//package com.ll.sbb.Admin;
//
//import com.ll.sbb.User.SiteUser;
//import com.ll.sbb.User.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AdminService {
//
//    private final AdminRepository adminRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public Admin createAdmin(String username, String password) {
//        Admin admin = new Admin();
//        admin.setUsername(username);
//        admin.setPassword(password);
//        return adminRepository.save(admin);
//    }
//
//    public boolean authenticateUser(String username, String password) {
//        Optional<Admin> adminOptional = adminRepository.findByusername(username);
//        if (adminOptional.isPresent()) {
//            Admin admin = adminOptional.get();
//            return passwordEncoder.matches(password, admin.getPassword());
//        }
//        return false;
//    }
//}