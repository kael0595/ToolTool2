package com.ll.sbb.Admin;

import com.ll.sbb.Review.Article;
import com.ll.sbb.Review.ArticleService;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import com.ll.sbb.Notice.Notice;
import com.ll.sbb.Notice.NoticeService;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserRole;
import com.ll.sbb.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final ArticleService articleService;

    private final MarketService marketService;

    private final UserService userService;

    private final NoticeService noticeService;

    @GetMapping("/admin")
    public String adminroot(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        List<Notice> noticeList = this.noticeService.getAll();
        List<SiteUser> userList = this.userService.getAll();
        List<SiteUser> adminList = this.userService.getUserByUserRole(UserRole.ADMIN);
        List<Article> articleList = this.articleService.getAll();
        List<Market> marketList = this.marketService.getAll();

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("articleList", articleList);
        model.addAttribute("marketList", marketList);
        model.addAttribute("userList", userList);
        model.addAttribute("adminList", adminList);

        return "AdminPage";
    }

    @GetMapping("/notice/delete/{id}")
    public String noticeDelete(@PathVariable("id") Integer id) {
        Notice notice = this.noticeService.getNotice(id);
        this.noticeService.delete(notice);
        return "redirect:/admin/";
    }

    @PostMapping("/addAdminRole")
    public String addAdminRole(@RequestParam("id") int id) {
        // 관리자 권한을 부여할 유저를 조회합니다.
        SiteUser user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/admin/user";
        }

        user.setUserRole(UserRole.ADMIN);
        userService.saveUser(user);

        return "redirect:/admin/user";
    }

    @PostMapping("/minusAdminRole")
    public String minusAdminRole(@RequestParam("id") int id) {

        SiteUser user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/admin/user";
        }

        user.setUserRole(UserRole.USER);
        userService.saveUser(user);

        return "redirect:/admin/user";
    }

    @GetMapping("/review")
    private String admin_review(Model model) {
        List<Article> articleList = this.articleService.getAll();
        model.addAttribute("articleList", articleList);
        return "/admin_review";
    }

    @GetMapping("/review/delete/{id}")
    public String reviewDelete(@PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);
        this.articleService.delete(article);
        return "redirect:/admin/review";
    }

    @GetMapping("/market")
    private String admin_market(Model model) {
        List<Market> marketList = this.marketService.getAll();
        model.addAttribute("marketList", marketList);
        return "/admin_market";
    }

    @GetMapping("/market/delete/{id}")
    public String marketDelete(@PathVariable("id") Integer id) {
        Market market = this.marketService.getMarket(id);
        this.marketService.delete(market);
        return "redirect:/admin/market";
    }

    @GetMapping("/user")
    private String admin_user(Model model) {
        List<SiteUser> userList = this.userService.getAll();
        model.addAttribute("userList", userList);
        return "/admin_user";
    }

    @GetMapping("/admin")
    private String admin(Model model) {
        List<SiteUser> adminList = this.userService.getUserByUserRole(UserRole.ADMIN);
        List<SiteUser> userList = this.userService.getAll();
        model.addAttribute("adminList", adminList);
        model.addAttribute("userList", userList);
        return "/admin";
    }
}