package com.ll.sbb.Article;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.ll.sbb.Category.subCategory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.ll.sbb.Answer.AnswerForm;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Article> paging = this.articleService.getList(page, kw);
        List<Article> articles = this.articleService.getAll();
        int articleCount = articles.size();
        model.addAttribute("articles", articles);
        model.addAttribute("articleCount", articleCount);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("list/category/{mainCategory}")
    public String mainCategory(Model model, @PathVariable("mainCategory") String mainCategory, @RequestParam(value = "subCategory", required = false) List<subCategory> subCategory) {
        List<Article> articleList;
        if (subCategory == null || subCategory.isEmpty()) {
            articleList = articleService.getMainCategory(mainCategory);
            model.addAttribute("articles", articleList);
        }
        if (mainCategory.equals("type")) {
            if (subCategory.contains("tent")) {
                List<Article> tentArticles = articleService.getByType("tent");
                model.addAttribute("articles", tentArticles);
            } else if (subCategory.contains("table")) {
                List<Article> tableArticles = articleService.getByType("table");
                model.addAttribute("articles", tableArticles);
            } else if (subCategory.contains("chair")) {
                List<Article> chairArticles = articleService.getByType("chair");
                model.addAttribute("articles", chairArticles);
            } else if (subCategory.contains("lantern")) {
                List<Article> lanternArticles = articleService.getByType("lantern");
                model.addAttribute("articles", lanternArticles);
            } else if (subCategory.contains("cookware")) {
                List<Article> cookwareArticles = articleService.getByType("cookware");
                model.addAttribute("articles", cookwareArticles);
            } else if (subCategory.contains("etc")) {
                List<Article> etcArticles = articleService.getByType("etc");
                model.addAttribute("articles", etcArticles);
            }
        } else if (mainCategory.equals("pricerange")) {
            List<Article> articles = articleService.getAll();
            List<Article> priceRangeArticles = new ArrayList<>();

            for (Article article : articles) {
                int price = article.getPrice();

                if (price <= 150000) {
                    if (subCategory.contains("15만원 이하")) {
                        priceRangeArticles.add(article);
                    }
                } else if (price <= 300000) {
                    if (subCategory.contains("30만원 이하")) {
                        priceRangeArticles.add(article);
                    }
                } else if (price <= 500000) {
                    if (subCategory.contains("50만원 이하")) {
                        priceRangeArticles.add(article);
                    }
                } else {
                    if (subCategory.contains("그 외")) {
                        priceRangeArticles.add(article);
                    }
                }
            }
        } else if (mainCategory.equals("season")) {
            if (subCategory.contains("summer")) {
                List<Article> summerArticles = articleService.getBySeason("summer");
                model.addAttribute("articles", summerArticles);
            } else if (subCategory.contains("winter")) {
                List<Article> winterArticles = articleService.getBySeason("winter");
                model.addAttribute("articles", winterArticles);
            } else if (subCategory.contains("all")) {
                List<Article> allSeasonArticles = articleService.getBySeason("all");
                model.addAttribute("articles", allSeasonArticles);
            }
        } else if (mainCategory.equals("beginner")) {

        } else if (mainCategory.equals("all")) {
            List<Article> allArticles = articleService.getAll();
            model.addAttribute("articles", allArticles);
        }
        model.addAttribute("mainCategory", mainCategory);
        model.addAttribute("subCategory", subCategory);
        return "article_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm) {
        return "article_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        this.articleService.create(articleForm.getSubject(), articleForm.getContent(), articleForm.getPrice());
        return "redirect:/article/list"; // 질문 저장후 질문목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String articleModify(ArticleForm articleForm, @PathVariable("id") Integer id, Principal principal) {
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleForm.setSubject(article.getSubject());
        articleForm.setContent(article.getContent());
        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String articleModify(@Valid ArticleForm articleForm, BindingResult bindingResult,
                                Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.articleService.modify(article, articleForm.getSubject(), articleForm.getContent());
        return String.format("redirect:/article/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String articleDelete(Principal principal, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.articleService.delete(article);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String articleVote(Principal principal, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.articleService.vote(article, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}