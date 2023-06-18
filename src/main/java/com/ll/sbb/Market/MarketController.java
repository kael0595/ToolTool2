package com.ll.sbb.Market;


import com.ll.sbb.Article.Article;
import com.ll.sbb.Category.subCategory;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/market")
@RequiredArgsConstructor
@Controller
public class MarketController {

    private final MarketService marketService;

    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Market> paging = this.marketService.getList(page, kw);
        List<Market> markets = this.marketService.getAll();
        int marketCount = markets.size();
        model.addAttribute("markets", markets);
        model.addAttribute("marketCount", marketCount);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "MarketPage";
    }

    @GetMapping(value = "/list/under/{id}")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, @PathVariable("id") Integer price) {
        int min;
        int max;
        if (price == 15) {
            min = 0;
            max = 150000;
            Page<Market> paging = this.marketService.getPriceList(page, kw, min, max);
            List<Market> articles = this.marketService.getByPrice(min, max);
            // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (price == 30) {
            min = 150000;
            max = 300000;
            Page<Market> paging = this.marketService.getPriceList(page, kw, min, max);
            List<Market> articles = this.marketService.getByPrice(min, max);
            // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (price == 50) {
            min = 300000;
            max = 500000;
            Page<Market> paging = this.marketService.getPriceList(page, kw, min, max);
            List<Market> articles = this.marketService.getByPrice(min, max);
            // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else {
            min = 500000;
            max = 5000000;
            Page<Market> paging = this.marketService.getPriceList(page, kw, min, max);
            List<Market> articles = this.marketService.getByPrice(min, max);
            // 받은 url값을 기준으로 미니멈, 맥시멈 값을 지정하여 서비스로 넘김 .
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        }

        return "MarketPage";
    }

    // 시즌,타입  카데고리 리스트 맵핑 시즌=serson , 타입=type

    @GetMapping("/list/{category}")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, @PathVariable("category") String category) {
        if (category.equals("season_all")) {
            Page<Market> paging = this.marketService.getSeasonList(page, kw, "사계");
            List<Market> articles = this.marketService.getBySeason("사계");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("season_summer")) {
            Page<Market> paging = this.marketService.getSeasonList(page, kw, "하계");
            List<Market> articles = this.marketService.getBySeason("하계");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);

        } else if (category.equals("season_winter")) {
            Page<Market> paging = this.marketService.getSeasonList(page, kw, "동계");
            List<Market> articles = this.marketService.getBySeason("동계");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_tent")) {
            Page<Market> paging = this.marketService.getTypeList(page, kw, "텐트/타프");
            List<Market> articles = this.marketService.getByType("텐트/타프");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_table")) {
            Page<Market> paging = this.marketService.getTypeList(page, kw, "테이블");
            List<Market> articles = this.marketService.getByType("테이블");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_chair")) {
            Page<Market> paging = this.marketService.getTypeList(page, kw, "의자");
            List<Market> articles = this.marketService.getByType("의자");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_lanturn")) {
            Page<Market> paging = this.marketService.getTypeList(page, kw, "랜턴");
            List<Market> articles = this.marketService.getByType("랜턴");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_cook")) {
            Page<Market> paging = this.marketService.getTypeList(page, kw, "조리도구");
            List<Market> articles = this.marketService.getByType("조리도구");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        } else if (category.equals("type_etc")) {
            Page<Market> paging = this.marketService.getTypeList(page, kw, "기타");
            List<Market> articles = this.marketService.getByType("기타");
            int articleCount = articles.size();
            model.addAttribute("articles", articles);
            model.addAttribute("articleCount", articleCount);
            model.addAttribute("paging", paging);
            model.addAttribute("kw", kw);
        }


        return "article_list";
    }


//    @GetMapping("/category/{mainCategory}")
//    public String mainCategory(Model model, @PathVariable("mainCategory") String mainCategory, @RequestParam(value = "subCategory", required = false) List<subCategory> subCategory) {
//        List<Market> marketList;
//        if (subCategory == null || subCategory.isEmpty()) {
//            marketList = marketService.getMainCategory(mainCategory);
//            model.addAttribute("marketList", marketList);
//        }
//        if (mainCategory.equals("type")) {
//            if (subCategory.contains("tent")) {
//                List<Market> tentMarketList = marketService.findByType("tent");
//                model.addAttribute("marketList", tentMarketList);
//            } else if (subCategory.contains("table")) {
//                List<Market> tableMarketList = marketService.findByType("table");
//                model.addAttribute("marketList", tableMarketList);
//            } else if (subCategory.contains("chair")) {
//                List<Market> chairMarketList = marketService.findByType("chair");
//                model.addAttribute("marketList", chairMarketList);
//            } else if (subCategory.contains("lantern")) {
//                List<Market> lanternMarketList = marketService.findByType("lantern");
//                model.addAttribute("marketList", lanternMarketList);
//            } else if (subCategory.contains("cookware")) {
//                List<Market> cookwareMarketList = marketService.findByType("cookware");
//                model.addAttribute("marketList", cookwareMarketList);
//            } else if (subCategory.contains("etc")) {
//                List<Market> etcMarketList = marketService.findByType("etc");
//                model.addAttribute("marketList", etcMarketList);
//            }
//        } else if (mainCategory.equals("pricerange")) {
//            List<Market> markets = marketService.getAll(); // 기사 전체 목록 가져오기
//            List<Market> priceRangemarketList = new ArrayList<>(); // 가격 범위에 해당하는 기사 저장할 리스트
//
//            for (Market market : markets) {
//                int price = market.getPrice();
//
//                if (price <= 150000) {
//                    if (subCategory.contains("15만원 이하")) {
//                        priceRangemarketList.add(market);
//                    }
//                } else if (price <= 300000) {
//                    if (subCategory.contains("30만원 이하")) {
//                        priceRangemarketList.add(market);
//                    }
//                } else if (price <= 500000) {
//                    if (subCategory.contains("50만원 이하")) {
//                        priceRangemarketList.add(market);
//                    }
//                } else {
//                    if (subCategory.contains("그 외")) {
//                        priceRangemarketList.add(market);
//                    }
//                }
//            }
//        } else if (mainCategory.equals("season")) {
//            if (subCategory.contains("summer")) {
//                List<Market> summerMarketList = marketService.findBySeason("summer");
//                model.addAttribute("MarketList", summerMarketList);
//            } else if (subCategory.contains("winter")) {
//                List<Market> winterMarketList = marketService.findBySeason("winter");
//                model.addAttribute("MarketList", winterMarketList);
//            } else if (subCategory.contains("all")) {
//                List<Market> allSeasonMarketList = marketService.getAll();
//                model.addAttribute("MarketList", allSeasonMarketList);
//            }
//        } else if (mainCategory.equals("beginner")) {
//
//        } else if (mainCategory.equals("all")) {
//            List<Market> allMarketList = marketService.getAll();
//            model.addAttribute("MarketList", allMarketList);
//        }
//        model.addAttribute("mainCategory", mainCategory);
//        model.addAttribute("subCategory", subCategory);
//
//        return "MarketPage";
//    }


    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Market market = this.marketService.getMarket(id);
        model.addAttribute("market", market);
        return "market_detail";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String marketCreate(MarketForm marketForm) {
        return "market_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String marketCreate(@Valid MarketForm marketForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "market_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.marketService.create(marketForm.getSubject(), marketForm.getContent(), marketForm.getPrice(), marketForm.getBrand(), marketForm.getType(), marketForm.getSeason(), siteUser);
        return "redirect:/market/list"; // 질문 저장후 질문목록으로 이동
    }

}