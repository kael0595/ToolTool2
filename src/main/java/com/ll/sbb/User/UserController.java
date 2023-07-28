package com.ll.sbb.User;

import com.ll.sbb.Review.Article;
import com.ll.sbb.Review.ArticleService;
import com.ll.sbb.Email.MailController;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

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
            UserRole role = userCreateForm.getUsername().startsWith("admin") ? UserRole.SUPER_ADMIN : UserRole.USER;
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

        Page<Market> marketVoterPaging = this.marketService.getUserVoterList(page, kw, siteUser.getUsername());

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
        model.addAttribute("marketVoterPaging", marketVoterPaging);
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
            mailController.sendEmailForPw(userEmail, user.getUsername());
            return "true";
        } else {
            return "redirect:/user/find";
        }
    }

    @PostMapping("/mypage/changePassword")
    @ResponseBody
    public String changePassword(@RequestParam("newpassword") String newpw, @RequestParam("newpasswordcf") String newpwcf) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        SiteUser user = userService.getUserByUsername(username);

        if (!newpw.equals(newpwcf)) {
            return "변경할 비밀번호와 확인 비밀번호가 일치하지 않습니다.";
        }
        user.setPassword(passwordEncoder.encode(newpw));
        userService.saveUser(user);
        return "/mypage";
    }

    @PostMapping("/mypage/changeNickname")
    @ResponseBody
    public String changeNickname(@RequestParam("newnickname") String newnickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        SiteUser user = userService.getUserByUsername(username);
        user.setNickname(newnickname);
        userService.saveUser(user);
        return "/mypage";
    }

    @PostMapping("/mypage/changeEmail")
    @ResponseBody
    public String changeEmail(@RequestParam("newemail") String newemail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        SiteUser user = userService.getUserByUsername(username);
        user.setEmail(newemail);
        return "/mypage";
    }

    @PostMapping("/mypage/changePhoto")
    @ResponseBody
    public String changePhoto(@RequestParam("file") MultipartFile file) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        SiteUser user = userService.getUserByUsername(username);
        userService.changePhoto(user, file);
        return "redirect:/mypage";
    }
}