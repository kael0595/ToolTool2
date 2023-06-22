package com.ll.sbb.Admin;

import com.ll.sbb.Article.Article;
import com.ll.sbb.Article.ArticleService;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import com.ll.sbb.Notice.Notice;
import com.ll.sbb.Notice.NoticeService;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserCreateForm;
import com.ll.sbb.User.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

//    private final ArticleService articleService;
//
//    private final MarketService marketService;
//
//    private final UserService userService;
//
//    private final NoticeService noticeService;

    @GetMapping("/")
    public String adminroot() {
//        SiteUser siteUser = this.userService.getUser(principal.getName());
//        Page<Article> paging = this.articleService.getList(page, kw);
//        List<Article> articles = this.articleService.getAll();
//        List<Market> markets = this.marketService.getAll();
//        List<Notice> notices = this.noticeService.getAll();
//        int articleCount = articles.size();
//        int marketCount = markets.size();
//        model.addAttribute("markets", markets);
//        model.addAttribute("marketCount", marketCount);
//        model.addAttribute("articles", articles);
//        model.addAttribute("articleCount", articleCount);
//        model.addAttribute("notices", notices);
//        model.addAttribute("paging", paging);
//        model.addAttribute("kw", kw);

        return "AdminPage";
    }

}