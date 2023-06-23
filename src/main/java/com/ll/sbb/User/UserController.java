package com.ll.sbb.User;

import com.ll.sbb.Article.Article;
import com.ll.sbb.Article.ArticleService;
import com.ll.sbb.Email.MailController;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ArticleService articleService;

    private final MarketService marketService;

    private final UserService userService;

    private final MailController mailController;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user/signup_menual")
    private String signup_menual() {
        return "signup_menual";
    }

    @GetMapping("/user/signup")
    private String signup(UserCreateForm userCreateForm) {
//        return "mailCheck";
        return "signup_form";
    }

    @PostMapping("/user/signup")
    private String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        System.out.println(userCreateForm.toString());
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "비밀번호 2개가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            UserRole role = userCreateForm.getUsername().startsWith("admin") ? UserRole.ADMIN : UserRole.USER;
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getPassword1(), userCreateForm.getEmail(), userCreateForm.getNickname(), userCreateForm.getMailKey(), role);
            userService.emailConfirm(userCreateForm.getEmail(), userCreateForm.getMailKey());
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
            session.setAttribute("user", user);

            return "redirect:/";
        } else {
            return "login_form";
        }
    }


    @GetMapping("/login/oauth2/code/kakao")
    public @ResponseBody String kakaoCallback(String code) {

        //POST방식으로 key=value 데이터 요청(카카오쪽으로)
        //Retrofit2
        //OkHttp
        //RestTemplate
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant-type", "authorization_code");
        params.add("client_key", "52a81a61b5875bfb11fea3e2e2aa2450");
        params.add("redirect_url", "http://localhost:8080/login/oauth2/code/kakao");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http 요청하기 - POST 방식으로 그리고 response 변수의 응답
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token"
                , HttpMethod.POST, kakaoTokenRequest, String.class);
        return "카카오 토큰 요청 완료 : 토큰에 대한 응답 : " + response;
    }

    @GetMapping("/user/logout")
    public String Logout(HttpSession session) {
        session.removeAttribute("loggedIn");
        return "redirect:/";
    }

    @GetMapping("/mypage")
    private String mypage(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "kw", defaultValue = "") String kw, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Page<Article> articlePaging = this.articleService.getUserList(page, kw, siteUser.getUsername());
        Page<Market> marketPaging = this.marketService.getUserList(page, kw, siteUser.getUsername());

        List<Article> articles = this.articleService.getAuthor(siteUser);
        List<Market> markets = this.marketService.getAuthor(siteUser);

        int articleCount = articles.size();
        int marketCount = markets.size();
        model.addAttribute("markets", markets);
        model.addAttribute("marketCount", marketCount);
        model.addAttribute("articles", articles);
        model.addAttribute("articleCount", articleCount);
        model.addAttribute("articlePaging", articlePaging);
        model.addAttribute("marketPaging", marketPaging);
        model.addAttribute("kw", kw);
        model.addAttribute("user", siteUser);
        return "mypage";
    }

    @GetMapping("/mypage/article")
    @ResponseBody
    private Model mypageArticlePaging(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "kw", defaultValue = "") String kw, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Page<Article> articlePaging = this.articleService.getUserList(page, kw, siteUser.getUsername());
        List<Article> articles = this.articleService.getAuthor(siteUser);
        int articleCount = articles.size();
        model.addAttribute("articlePaging", articlePaging);
        model.addAttribute("articles", articles);
        model.addAttribute("articleCount", articleCount);
        return model;

    }

    @GetMapping("/mypage/market")
    @ResponseBody
    private Model mypageMarketPaging(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "kw", defaultValue = "") String kw, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Page<Market> marketPaging = this.marketService.getUserList(page, kw, siteUser.getUsername());
        List<Market> markets = this.marketService.getAuthor(siteUser);
        int marketCount = markets.size();
        model.addAttribute("marketPaging", marketPaging);
        model.addAttribute("markets", markets);
        model.addAttribute("marketCount", marketCount);
        return model;

    }

    @GetMapping("/user/find")
    public String find() {
        return "UserSearch";
    }

    @PostMapping("/user/findId")
    @ResponseBody
    public String findId(@RequestParam("email") String email) {
        SiteUser user = userService.getUserByEmail(email);

        if (user != null) {
            mailController.sendEmailForId(email, user.getUsername());
            return "true";
        } else {
            return "redirect:/user/find";
        }
    }


    @PostMapping("/user/findPw")
    @ResponseBody
    public String findPw(@RequestParam("userEmail") String userEmail, @RequestParam("userName") String userName) {
        SiteUser user = userService.getUserByEmailAndUsername(userEmail, userName);
        if (user != null) {
            // 유저를 찾았을 때, 임시 비밀번호를 이메일로 전송하는 메서드 호출
            mailController.sendEmailForPw(userEmail, user.getUsername());
            return "true";
        } else {
            return "redirect:/user/find";
        }
    }

    @PostMapping("/user/changePassword")
    @ResponseBody
    public String updatePassword(@RequestParam("password") String pw, @RequestParam("newpassword") String newpw, @RequestParam("newpasswordcf") String newpwcf) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        SiteUser user = userService.getUserByUsername(username);

        if (!passwordEncoder.matches(pw, user.getPassword())) {
            return "현재 비밀번호와 일치하지 않습니다.";
        }
        if (!newpw.equals(newpwcf)) {
            return "변경할 비밀번호와 확인 비밀번호가 일치하지 않습니다.";
        }
        user.setPassword(passwordEncoder.encode(newpw));
        userService.saveUser(user);
        return "success";
    }

    public void updatePassword(@RequestParam("password") String password, @RequestParam("email") String email) {
        userService.updatePassword(password, email);
    }
}