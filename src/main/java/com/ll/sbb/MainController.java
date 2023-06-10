package com.ll.sbb;

import com.ll.sbb.Article.Article;
import com.ll.sbb.Article.ArticleService;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final ArticleService articleService;

    private final MarketService marketService;

    @GetMapping("/")
    public String list(Model model, Authentication authentication) {
        List<Article> articleList = this.articleService.getAll();
        List<Market> marketList = this.marketService.getAll();
        model.addAttribute("marketList", marketList);
        model.addAttribute("articleList", articleList);

        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }

        return "MainPage";
    }


}
