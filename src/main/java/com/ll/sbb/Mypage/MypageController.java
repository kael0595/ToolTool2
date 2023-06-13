package com.ll.sbb.Mypage;

import com.ll.sbb.Article.Article;
import com.ll.sbb.Article.ArticleController;
import com.ll.sbb.Article.ArticleService;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final ArticleService articleService;

    private final MarketService marketService;

    @GetMapping("/mypage")
    private String mypage(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Article> paging = this.articleService.getList(page, kw);
        List<Article> articles = this.articleService.getAll();
        List<Market> markets = this.marketService.getAll();
        int articleCount = articles.size();
        int marketCount = markets.size();
        model.addAttribute("markets", markets);
        model.addAttribute("marketCount", marketCount);
        model.addAttribute("articles", articles);
        model.addAttribute("articleCount", articleCount);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "mypage";
    }
}
