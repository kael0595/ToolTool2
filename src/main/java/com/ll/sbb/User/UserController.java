package com.ll.sbb.User;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup_menual")
    private String signup_manual() {
        return "signup_menual";
    }

    @GetMapping("/signup")
    private String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    private String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "비밀번호 2개가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getPassword1(), userCreateForm.getEmail(), userCreateForm.getNickname());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @PostMapping("/login")
    public String processLogin(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        if (userService.authenticateUser(username, password)) {
            session.setAttribute("loggedIn", true);
            return "redirect:/";
        } else {
            return "login_form";
        }
    }


    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        // 로그아웃 로직 처리
        session.removeAttribute("loggedIn");
        return "redirect:/";
    }

}
