package com.ll.sbb.Market;

import com.ll.sbb.Article.Article;
import com.ll.sbb.Article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketSortController {
    private final MarketService marketService;

    @GetMapping("/listCategory")
    public String list(Model model, @RequestParam("sort") String sort, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        String sortKey;
        if (sort.equals("createSort")) {
            sortKey = "createDate";
            Page<Market> paging = this.marketService.getHigtList(page, kw, sortKey);
            List<Market> markets = this.marketService.getAll();
            int marketCount = markets.size();
            model.addAttribute("articles", markets);
            model.addAttribute("marketCount", marketCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (sort.equals("rowPriceSort")) {
            sortKey = "price";
            Page<Market> paging = this.marketService.getRowList(page, kw, sortKey);
            List<Market> markets = this.marketService.getAll();
            int marketCount = markets.size();
            model.addAttribute("articles", markets);
            model.addAttribute("marketCount", marketCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (sort.equals("highPriceSort")) {
            sortKey = "price";
            Page<Market> paging = this.marketService.getHigtList(page, kw, sortKey);
            List<Market> markets = this.marketService.getAll();
            int marketCount = markets.size();
            model.addAttribute("articles", markets);
            model.addAttribute("marketCount", marketCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (sort.equals("voterSort")) {
            sortKey = "likeCount";
            Page<Market> paging = this.marketService.getHigtList(page, kw, sortKey);
            List<Market> markets = this.marketService.getAll();
            int marketCount = markets.size();
            model.addAttribute("articles", markets);
            model.addAttribute("marketCount", marketCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        }

        return "market_item";
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
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 30) {
                min = 150000;
                max = 300000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        } else if (sort.equals("rowPriceSort")) {
            sortKey = "price";
            if (price == 15) {
                min = 0;
                max = 150000;
                Page<Market> paging = this.marketService.getPriceRowList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 30) {
                min = 150000;
                max = 300000;
                Page<Market> paging = this.marketService.getPriceRowList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Market> paging = this.marketService.getPriceRowList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Market> paging = this.marketService.getPriceRowList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }

        } else if (sort.equals("highPriceSort")) {
            sortKey = "price";
            if (price == 15) {
                min = 0;
                max = 150000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 30) {
                min = 150000;
                max = 300000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        }else if (sort.equals("voterSort")) {
            sortKey = "likeCount";
            if (price == 15) {
                min = 0;
                max = 150000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 30) {
                min = 150000;
                max = 300000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (price == 50) {
                min = 300000;
                max = 500000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else {
                min = 500000;
                max = 5000000;
                Page<Market> paging = this.marketService.getPriceHigtList(page, kw, min, max, sortKey);
                List<Market> markets = this.marketService.getByPrice(min, max);
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        }


        return "market_item";
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
                Page<Market> paging = this.marketService.getSeasonHighList(page, kw, "사계", sortKey);
                List<Market> markets = this.marketService.getBySeason("사계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Market> paging = this.marketService.getSeasonHighList(page, kw, "하계", sortKey);
                List<Market> markets = this.marketService.getBySeason("하계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Market> paging = this.marketService.getSeasonHighList(page, kw, "동계", sortKey);
                List<Market> markets = this.marketService.getBySeason("동계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "텐트/타프", sortKey);
                List<Market> markets = this.marketService.getByType("텐트/타프");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "테이블", sortKey);
                List<Market> markets = this.marketService.getByType("테이블");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "의자", sortKey);
                List<Market> markets = this.marketService.getByType("의자");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "랜턴", sortKey);
                List<Market> markets = this.marketService.getByType("랜턴");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "조리도구", sortKey);
                List<Market> markets = this.marketService.getByType("조리도구");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "기타", sortKey);
                List<Market> markets = this.marketService.getByType("기타");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        } else if (sort.equals("highPriceSort")) {
            sortKey = "price";
            if (category.equals("season_all")) {
                Page<Market> paging = this.marketService.getSeasonHighList(page, kw, "사계", sortKey);
                List<Market> markets = this.marketService.getBySeason("사계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Market> paging = this.marketService.getSeasonHighList(page, kw, "하계", sortKey);
                List<Market> markets = this.marketService.getBySeason("하계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Market> paging = this.marketService.getSeasonHighList(page, kw, "동계", sortKey);
                List<Market> markets = this.marketService.getBySeason("동계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "텐트/타프", sortKey);
                List<Market> markets = this.marketService.getByType("텐트/타프");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "테이블", sortKey);
                List<Market> markets = this.marketService.getByType("테이블");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "의자", sortKey);
                List<Market> markets = this.marketService.getByType("의자");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "랜턴", sortKey);
                List<Market> markets = this.marketService.getByType("랜턴");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "조리도구", sortKey);
                List<Market> markets = this.marketService.getByType("조리도구");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Market> paging = this.marketService.getTypeHighList(page, kw, "기타", sortKey);
                List<Market> markets = this.marketService.getByType("기타");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        } else if (sort.equals("rowPriceSort")) {
            sortKey = "price";
            if (category.equals("season_all")) {
                Page<Market> paging = this.marketService.getSeasonRowList(page, kw, "사계", sortKey);
                List<Market> markets = this.marketService.getBySeason("사계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Market> paging = this.marketService.getSeasonRowList(page, kw, "하계", sortKey);
                List<Market> markets = this.marketService.getBySeason("하계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Market> paging = this.marketService.getSeasonRowList(page, kw, "동계", sortKey);
                List<Market> markets = this.marketService.getBySeason("동계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "텐트/타프", sortKey);
                List<Market> markets = this.marketService.getByType("텐트/타프");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "테이블", sortKey);
                List<Market> markets = this.marketService.getByType("테이블");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "의자", sortKey);
                List<Market> markets = this.marketService.getByType("의자");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "랜턴", sortKey);
                List<Market> markets = this.marketService.getByType("랜턴");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "조리도구", sortKey);
                List<Market> markets = this.marketService.getByType("조리도구");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "기타", sortKey);
                List<Market> markets = this.marketService.getByType("기타");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        }else if (sort.equals("voterSort")) {
            sortKey = "likeCount";
            if (category.equals("season_all")) {
                Page<Market> paging = this.marketService.getSeasonRowList(page, kw, "사계", sortKey);
                List<Market> markets = this.marketService.getBySeason("사계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("season_summer")) {
                Page<Market> paging = this.marketService.getSeasonRowList(page, kw, "하계", sortKey);
                List<Market> markets = this.marketService.getBySeason("하계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);

            } else if (category.equals("season_winter")) {
                Page<Market> paging = this.marketService.getSeasonRowList(page, kw, "동계", sortKey);
                List<Market> markets = this.marketService.getBySeason("동계");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_tent")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "텐트/타프", sortKey);
                List<Market> markets = this.marketService.getByType("텐트/타프");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_table")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "테이블", sortKey);
                List<Market> markets = this.marketService.getByType("테이블");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_chair")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "의자", sortKey);
                List<Market> markets = this.marketService.getByType("의자");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_lanturn")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "랜턴", sortKey);
                List<Market> markets = this.marketService.getByType("랜턴");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_cook")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "조리도구", sortKey);
                List<Market> markets = this.marketService.getByType("조리도구");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            } else if (category.equals("type_etc")) {
                Page<Market> paging = this.marketService.getTypeRowList(page, kw, "기타", sortKey);
                List<Market> markets = this.marketService.getByType("기타");
                int marketCount = markets.size();
                model.addAttribute("articles", markets);
                model.addAttribute("marketCount", marketCount);
                model.addAttribute("paging", paging);
                model.addAttribute("kw", kw);
            }
        }


        return "market_item";
    }
}
