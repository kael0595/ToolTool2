package com.ll.sbb.Admin;

import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserCreateForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
//
//    private AdminService adminService;
//
//    @GetMapping("/signup")
//    public String createAdmin(AdminCreateForm adminCreateForm) {
//        return "admin_signup_form";
//    }
//
//    @PostMapping("/signup")
//    public String createAdmin(@Valid AdminCreateForm adminCreateForm, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "admin_signup_form";
//        }
//        if (!adminCreateForm.getPassword1().equals(adminCreateForm.getPassword2())) {
//            bindingResult.rejectValue("password2", "비밀번호 2개가 일치하지 않습니다.");
//            return "admin_signup_form";
//        }
//        try {
//            adminService.createAdmin(adminCreateForm.getUsername(), adminCreateForm.getPassword1());
//        } catch (DataIntegrityViolationException e) {
//            e.printStackTrace();
//            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
//            return "admin_signup_form";
//        } catch (Exception e) {
//            e.printStackTrace();
//            bindingResult.reject("signupFailed", e.getMessage());
//            return "admin_signup_form";
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping("/login")
//    public String login() {
//
//        return "admin_login_form";
//    }
//
//    @PostMapping("/login")
//    public String processLogin(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
//        if (adminService.authenticateUser(username, password)) {
//            session.setAttribute("loggedIn", true);
//            return "redirect:/";
//        } else {
//            return "login_form";
//        }
//    }

    @GetMapping("/")
    public String adminroot() {
        return "AdminPage";
    }

}