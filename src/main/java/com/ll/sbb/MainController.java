package com.ll.sbb;

import com.ll.sbb.Review.Article;
import com.ll.sbb.Review.ArticleService;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final ArticleService articleService;

    private final MarketService marketService;

    @GetMapping("/")
    public String mainPage(Model model, HttpSession session) {
        Page<Article> articlePage = this.articleService.getMainList(0);
        Page<Market> marketPage = this.marketService.getMainList(0);

        List<Article> articleList = this.articleService.getAll();
        List<Market> marketList = this.marketService.getAll();
        model.addAttribute("marketList", marketList);
        model.addAttribute("articleList", articleList);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("marketPage", marketPage);

        boolean isAuthenticated = false;
        if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
            isAuthenticated = true;
        }
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "MainPage";
    }


}