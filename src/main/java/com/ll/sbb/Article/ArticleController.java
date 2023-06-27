package com.ll.sbb.Article;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Article> paging = this.articleService.getList(page, kw);
        List<Article> articles = this.articleService.getAll();
        int articleCount = paging.getNumberOfElements();
        model.addAttribute("articles", articles);
        model.addAttribute("articleCount", articleCount);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String articleDetail(Principal principal, Model model, @PathVariable("id") Integer id) {
        Article article = this.articleService.getArticle(id);
        boolean checkedLike = false;
        this.articleService.viewCountUp(article);
        if (principal != null) {
            SiteUser siteUser = this.userService.getUser(principal.getName());
            for (SiteUser voter : article.getVoter()) {
                if (voter.getId() == siteUser.getId()) {
                    checkedLike = true;
                }
            }
        }


        model.addAttribute("checkedLike", checkedLike);
        model.addAttribute("article", article);
        return "article_detail";
    }
//     가격범위 카데고리 리스트 맵핑

    @GetMapping(value = "/under/{id}/sort")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, @PathVariable("id") Integer price) {
        int min;
        int max;
        if (price == 15) {
            min = 0;
            max = 150000;
            Page<Article> paging = this.articleService.getPriceList(page, kw, min, max);
            List<Article> articles = this.articleService.getByPrice(min, max);
            // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (price == 30) {
            min = 150000;
            max = 300000;
            Page<Article> paging = this.articleService.getPriceList(page, kw, min, max);
            List<Article> articles = this.articleService.getByPrice(min, max);
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (price == 50) {
            min = 300000;
            max = 500000;
            Page<Article> paging = this.articleService.getPriceList(page, kw, min, max);
            List<Article> articles = this.articleService.getByPrice(min, max);
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else {
            min = 500000;
            max = 5000000;
            Page<Article> paging = this.articleService.getPriceList(page, kw, min, max);
            List<Article> articles = this.articleService.getByPrice(min, max);
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        }

        return "article_list";
    }

    // 시즌,타입  카데고리 리스트 맵핑 시즌=season , 타입=type

    @GetMapping("/{category}/sort")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, @PathVariable("category") String category) {
        if (category.equals("season_all")) {
            Page<Article> paging = this.articleService.getSeasonList(page, kw, "사계");
            List<Article> articles = this.articleService.getBySeason("사계");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("season_summer")) {
            Page<Article> paging = this.articleService.getSeasonList(page, kw, "하계");
            List<Article> articles = this.articleService.getBySeason("하계");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);

        } else if (category.equals("season_winter")) {
            Page<Article> paging = this.articleService.getSeasonList(page, kw, "동계");
            List<Article> articles = this.articleService.getBySeason("동계");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_tent")) {
            Page<Article> paging = this.articleService.getTypeList(page, kw, "텐트/타프");
            List<Article> articles = this.articleService.getByType("텐트/타프");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_table")) {
            Page<Article> paging = this.articleService.getTypeList(page, kw, "테이블");
            List<Article> articles = this.articleService.getByType("테이블");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_chair")) {
            Page<Article> paging = this.articleService.getTypeList(page, kw, "의자");
            List<Article> articles = this.articleService.getByType("의자");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_lanturn")) {
            Page<Article> paging = this.articleService.getTypeList(page, kw, "랜턴");
            List<Article> articles = this.articleService.getByType("랜턴");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_cook")) {
            Page<Article> paging = this.articleService.getTypeList(page, kw, "조리도구");
            List<Article> articles = this.articleService.getByType("조리도구");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_etc")) {
            Page<Article> paging = this.articleService.getTypeList(page, kw, "기타");
            List<Article> articles = this.articleService.getByType("기타");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        }


        return "article_list";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm) {
        return "article_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String articleCreate(@Valid ArticleForm articleForm,
                                BindingResult bindingResult,
                                Principal principal,
                                @RequestParam("files") MultipartFile[] files) throws Exception {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.articleService.create(articleForm, siteUser, files);
        return "redirect:/article/list";
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
        articleForm.setPrice(article.getPrice());
        articleForm.setType(article.getType());
        articleForm.setSeason(article.getSeason());
        articleForm.setStarScore(article.getStarScore());
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
        this.articleService.modify(article, articleForm.getSubject(), articleForm.getContent(), articleForm.getType(), articleForm.getSeason(), articleForm.getPrice(), articleForm.getStarScore());
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

    @PostMapping("/vote/{id}")
    @ResponseBody
    public int articleVote(Principal principal, @PathVariable("id") Integer id) {
        if (principal == null) {
            return -1;
        } else {
            Article article = this.articleService.getArticle(id);
            SiteUser siteUser = this.userService.getUser(principal.getName());

            int tempLikeCount = article.getLikeCount();

            if (article.getVoter().isEmpty()) {
                article.setLikeCount(tempLikeCount + 1);
                this.articleService.vote(article, siteUser);
            } else {
                for (SiteUser voter : article.getVoter()) {
                    if (voter.getId() == siteUser.getId()) {
                        article.setLikeCount(tempLikeCount - 1);
                        this.articleService.delVote(article, siteUser);
                    }
                }
            }

            // => Join Count(*)
            Article upadateArticle = this.articleService.getArticle(id);
            //
            return upadateArticle.getLikeCount();
        }
    }
}