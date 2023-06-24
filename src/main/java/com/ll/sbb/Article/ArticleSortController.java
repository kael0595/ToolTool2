package com.ll.sbb.Article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleSortController {

    private final ArticleService articleService;

    @GetMapping("/listCategory")
    public String list(Model model, @RequestParam("sort") String sort, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        String sortKey;
        if (sort.equals("createSort")) {
            sortKey = "createDate";
            Page<Article> paging = this.articleService.getHigtList(page, kw, sortKey);
            List<Article> articles = this.articleService.getAll();
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (sort.equals("starScoreSort")) {
            sortKey = "starScore";
            Page<Article> paging = this.articleService.getHigtList(page, kw, sortKey);
            List<Article> articles = this.articleService.getAll();
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (sort.equals("rowPriceSort")) {
            sortKey = "price";
            Page<Article> paging = this.articleService.getRowList(page, kw, sortKey);
            List<Article> articles = this.articleService.getAll();
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (sort.equals("highPriceSort")) {
            sortKey = "price";
            Page<Article> paging = this.articleService.getHigtList(page, kw, sortKey);
            List<Article> articles = this.articleService.getAll();
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        }else if (sort.equals("voterSort")) {
            sortKey = "likeCount";
            Page<Article> paging = this.articleService.getHigtList(page, kw, sortKey);
            List<Article> articles = this.articleService.getAll();
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        }

        return "article_item";
    }
//     가격범위 카데고리 리스트 맵핑


    @GetMapping(value = "under/{id}/listCategory")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, @PathVariable("id") Integer price,
                       @RequestParam("sort") String sort) {
        int min;
        int max;
        String sortKey;
        if (sort.equals("createSort")) {
            sortKey = "createDate";
            if (price == 15) {
                min = 0;
                max = 150000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
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
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        } else if (sort.equals("starScoreSort")) {
            sortKey = "starScore";
            if (price == 15) {
                min = 0;
                max = 150000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
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
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        } else if (sort.equals("rowPriceSort")) {
            sortKey = "price";
            if (price == 15) {
                min = 0;
                max = 150000;
                Page<Article> paging = this.articleService.getPriceRowList(page, kw, min, max, sortKey);
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
                Page<Article> paging = this.articleService.getPriceRowList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Article> paging = this.articleService.getPriceRowList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Article> paging = this.articleService.getPriceRowList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }

        } else if (sort.equals("highPriceSort")) {
            sortKey = "price";
            if (price == 15) {
                min = 0;
                max = 150000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
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
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        }else if (sort.equals("voterSort")) {
            sortKey = "likeCount";
            if (price == 15) {
                min = 0;
                max = 150000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
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
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Article> paging = this.articleService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Article> articles = this.articleService.getByPrice(min, max);
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        }


        return "article_item";
    }

    // 시즌,타입  카데고리 리스트 맵핑 시즌=season , 타입=type


    @GetMapping("/{category}/listCategory")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, @PathVariable("category") String category,
                       @RequestParam("sort") String sort) {
        String sortKey;
        if (sort.equals("createSort")) {
            sortKey = "createDate";
            if (category.equals("season_all")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "사계", sortKey);
                List<Article> articles = this.articleService.getBySeason("사계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "하계", sortKey);
                List<Article> articles = this.articleService.getBySeason("하계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "동계", sortKey);
                List<Article> articles = this.articleService.getBySeason("동계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "텐트/타프", sortKey);
                List<Article> articles = this.articleService.getByType("텐트/타프");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "테이블", sortKey);
                List<Article> articles = this.articleService.getByType("테이블");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "의자", sortKey);
                List<Article> articles = this.articleService.getByType("의자");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "랜턴", sortKey);
                List<Article> articles = this.articleService.getByType("랜턴");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "조리도구", sortKey);
                List<Article> articles = this.articleService.getByType("조리도구");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "기타", sortKey);
                List<Article> articles = this.articleService.getByType("기타");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        } else if (sort.equals("starScoreSort")) {
            sortKey = "starScore";
            if (category.equals("season_all")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "사계", sortKey);
                List<Article> articles = this.articleService.getBySeason("사계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "하계", sortKey);
                List<Article> articles = this.articleService.getBySeason("하계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "동계", sortKey);
                List<Article> articles = this.articleService.getBySeason("동계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "텐트/타프", sortKey);
                List<Article> articles = this.articleService.getByType("텐트/타프");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "테이블", sortKey);
                List<Article> articles = this.articleService.getByType("테이블");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "의자", sortKey);
                List<Article> articles = this.articleService.getByType("의자");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "랜턴", sortKey);
                List<Article> articles = this.articleService.getByType("랜턴");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "조리도구", sortKey);
                List<Article> articles = this.articleService.getByType("조리도구");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "기타", sortKey);
                List<Article> articles = this.articleService.getByType("기타");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        } else if (sort.equals("highPriceSort")) {
            sortKey = "price";
            if (category.equals("season_all")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "사계", sortKey);
                List<Article> articles = this.articleService.getBySeason("사계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "하계", sortKey);
                List<Article> articles = this.articleService.getBySeason("하계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "동계", sortKey);
                List<Article> articles = this.articleService.getBySeason("동계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "텐트/타프", sortKey);
                List<Article> articles = this.articleService.getByType("텐트/타프");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "테이블", sortKey);
                List<Article> articles = this.articleService.getByType("테이블");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "의자", sortKey);
                List<Article> articles = this.articleService.getByType("의자");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "랜턴", sortKey);
                List<Article> articles = this.articleService.getByType("랜턴");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "조리도구", sortKey);
                List<Article> articles = this.articleService.getByType("조리도구");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "기타", sortKey);
                List<Article> articles = this.articleService.getByType("기타");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        } else if (sort.equals("rowPriceSort")) {
            sortKey = "price";
            if (category.equals("season_all")) {
                Page<Article> paging = this.articleService.getSeasonRowList(page, kw, "사계", sortKey);
                List<Article> articles = this.articleService.getBySeason("사계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Article> paging = this.articleService.getSeasonRowList(page, kw, "하계", sortKey);
                List<Article> articles = this.articleService.getBySeason("하계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Article> paging = this.articleService.getSeasonRowList(page, kw, "동계", sortKey);
                List<Article> articles = this.articleService.getBySeason("동계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Article> paging = this.articleService.getTypeRowList(page, kw, "텐트/타프", sortKey);
                List<Article> articles = this.articleService.getByType("텐트/타프");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Article> paging = this.articleService.getTypeRowList(page, kw, "테이블", sortKey);
                List<Article> articles = this.articleService.getByType("테이블");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Article> paging = this.articleService.getTypeRowList(page, kw, "의자", sortKey);
                List<Article> articles = this.articleService.getByType("의자");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Article> paging = this.articleService.getTypeRowList(page, kw, "랜턴", sortKey);
                List<Article> articles = this.articleService.getByType("랜턴");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Article> paging = this.articleService.getTypeRowList(page, kw, "조리도구", sortKey);
                List<Article> articles = this.articleService.getByType("조리도구");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Article> paging = this.articleService.getTypeRowList(page, kw, "기타", sortKey);
                List<Article> articles = this.articleService.getByType("기타");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        }else if (sort.equals("voterSort")) {
            sortKey = "likeScore";
            if (category.equals("season_all")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "사계", sortKey);
                List<Article> articles = this.articleService.getBySeason("사계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "하계", sortKey);
                List<Article> articles = this.articleService.getBySeason("하계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Article> paging = this.articleService.getSeasonHighList(page, kw, "동계", sortKey);
                List<Article> articles = this.articleService.getBySeason("동계");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "텐트/타프", sortKey);
                List<Article> articles = this.articleService.getByType("텐트/타프");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "테이블", sortKey);
                List<Article> articles = this.articleService.getByType("테이블");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "의자", sortKey);
                List<Article> articles = this.articleService.getByType("의자");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "랜턴", sortKey);
                List<Article> articles = this.articleService.getByType("랜턴");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "조리도구", sortKey);
                List<Article> articles = this.articleService.getByType("조리도구");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Article> paging = this.articleService.getTypeHighList(page, kw, "기타", sortKey);
                List<Article> articles = this.articleService.getByType("기타");
                int articleCount = articles.size();
                model.addAttribute("articles", articles);
                model.addAttribute("articleCount", articleCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        }


        return "article_item";
    }
}