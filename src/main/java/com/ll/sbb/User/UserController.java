package com.ll.sbb.User;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/user/signup_menual")
    private String signup_manual() {
        return "signup_menual";
    }

    @GetMapping("/user/signup")
    private String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/user/signup")
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

    @GetMapping("/user/login")
    public String login() {
        return "login_form";
    }

    @PostMapping("/user/login")
    public String login(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        if (userService.authenticateUser(username, password)) {
            session.setAttribute("loggedIn", true);

            SiteUser user = userService.getUser(username);
//            user.setAdmin(2);
            session.setAttribute("user", user);

            return "redirect:/";
        } else {
            return "login_form";
        }
    }


//    @GetMapping("/login/oauth2/code/kakao")
//    public @ResponseBody String kakaoCallback(String code) {
//
//        //POST방식으로 key=value 데이터 요청(카카오쪽으로)
//        //Retrofit2
//        //OkHttp
//        //RestTemplate
//        RestTemplate rt = new RestTemplate();
//
//        //HttpHeader 오브젝트 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant-type", "authorization_code");
//        params.add("client_key", "52a81a61b5875bfb11fea3e2e2aa2450");
//        params.add("redirect_url", "http://localhost:8080/login/oauth2/code/kakao");
//        params.add("code", code);
//
//        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(params, headers);
//
//        //Http 요청하기 - POST 방식으로 그리고 response 변수의 응답
//        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token"
//                , HttpMethod.POST, kakaoTokenRequest, String.class);
//        return "카카오 토큰 요청 완료 : 토큰에 대한 응답 : " + response;
//    }


    @GetMapping("/user/logout")
    public String Logout(HttpSession session) {
        session.removeAttribute("loggedIn");
        return "redirect:/";
    }

}
