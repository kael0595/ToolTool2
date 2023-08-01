package com.ll.sbb.User;

import com.ll.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

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


    public SiteUser getUserByEmailAndUsername(String email, String username) {
        Optional<SiteUser> siteUserOptional = userRepository.findByEmailAndUsername(email, username);
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
        Optional<SiteUser> optionalUser = this.userRepository.findByUsername(username);
        return optionalUser.orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    public SiteUser saveUser(SiteUser user) {
        return userRepository.save(user);
    }

    public SiteUser getUserById(int id) {
        return userRepository.findById(id);
    }

    public List<SiteUser> getAll() {
        return this.userRepository.findAll();
    }

    public List<SiteUser> getUserByUserRole(UserRole admin) {
        return this.userRepository.findUserByUserRole(UserRole.ADMIN);
    }

    public void changePhoto(SiteUser user, MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "files";
        UUID uuid = UUID.randomUUID(); // 랜덤으로 이름을 만들어줄 수 있음
        // uuid는 파일에 붙일 랜덤이름을 생성

        String fileName = uuid + "_" + file.getOriginalFilename();
        // 랜덤이름(uuid)을 앞에다 붙이고 그 다음에 언더바(_) 하고 파일이름을 뒤에 붙여서 저장될 파일 이름을 생성해줌
        String filePath = "/files/" + fileName;

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);
        user.setFilepath(filePath);
        user.setFilename(fileName);
        this.userRepository.save(user);
    }

    public void emailConfirm(String email, int mailKey) throws Exception {
        SiteUser user = this.getUserByEmail(email);

        if (user != null && user.getMailKey() == mailKey) {
            updateMailAuth(email, mailKey);
        } else {
            throw new Exception("유효하지 않은 이메일 또는 메일 키입니다.");
        }
    }
}