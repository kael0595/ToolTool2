package com.ll.sbb.Market;


import com.ll.sbb.Article.Article;
import com.ll.sbb.Category.subCategory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/market")
@RequiredArgsConstructor
@Controller
public class MarketController {

    private final MarketService marketService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Market> paging = this.marketService.getList(page, kw);
        List<Market> markets = this.marketService.getAll();
        int marketCount = markets.size();
        model.addAttribute("markets", markets);
        model.addAttribute("articleCount", marketCount);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "market_list";
    }

    @GetMapping("/category/{mainCategory}")
    public String mainCategory(Model model, @PathVariable("mainCategory") String mainCategory, @RequestParam(value = "subCategory", required = false) List<subCategory> subCategory) {
        List<Market> marketList;
        if (subCategory == null || subCategory.isEmpty()) {
            marketList = marketService.getMainCategory(mainCategory);
            model.addAttribute("marketList", marketList);
        }
        if (mainCategory.equals("type")) {
            if (subCategory.contains("tent")) {
                List<Market> tentMarketList = marketService.findByType("tent");
                model.addAttribute("marketList", tentMarketList);
            } else if (subCategory.contains("table")) {
                List<Market> tableMarketList = marketService.findByType("table");
                model.addAttribute("marketList", tableMarketList);
            } else if (subCategory.contains("chair")) {
                List<Market> chairMarketList = marketService.findByType("chair");
                model.addAttribute("marketList", chairMarketList);
            } else if (subCategory.contains("lantern")) {
                List<Market> lanternMarketList = marketService.findByType("lantern");
                model.addAttribute("marketList", lanternMarketList);
            } else if (subCategory.contains("cookware")) {
                List<Market> cookwareMarketList = marketService.findByType("cookware");
                model.addAttribute("marketList", cookwareMarketList);
            } else if (subCategory.contains("etc")) {
                List<Market> etcMarketList = marketService.findByType("etc");
                model.addAttribute("marketList", etcMarketList);
            }
        } else if (mainCategory.equals("pricerange")) {
            List<Market> markets = marketService.getAll(); // 기사 전체 목록 가져오기
            List<Market> priceRangemarketList = new ArrayList<>(); // 가격 범위에 해당하는 기사 저장할 리스트

            for (Market market : markets) {
                int price = market.getPrice();

                if (price <= 150000) {
                    if (subCategory.contains("15만원 이하")) {
                        priceRangemarketList.add(market);
                    }
                } else if (price <= 300000) {
                    if (subCategory.contains("30만원 이하")) {
                        priceRangemarketList.add(market);
                    }
                } else if (price <= 500000) {
                    if (subCategory.contains("50만원 이하")) {
                        priceRangemarketList.add(market);
                    }
                } else {
                    if (subCategory.contains("그 외")) {
                        priceRangemarketList.add(market);
                    }
                }
            }
        } else if (mainCategory.equals("season")) {
            if (subCategory.contains("summer")) {
                List<Market> summerMarketList = marketService.findBySeason("summer");
                model.addAttribute("MarketList", summerMarketList);
            } else if (subCategory.contains("winter")) {
                List<Market> winterMarketList = marketService.findBySeason("winter");
                model.addAttribute("MarketList", winterMarketList);
            } else if (subCategory.contains("all")) {
                List<Market> allSeasonMarketList = marketService.getAll();
                model.addAttribute("MarketList", allSeasonMarketList);
            }
        } else if (mainCategory.equals("beginner")) {

        } else if (mainCategory.equals("all")) {
            List<Market> allMarketList = marketService.getAll();
            model.addAttribute("MarketList", allMarketList);
        }
        model.addAttribute("mainCategory", mainCategory);
        model.addAttribute("subCategory", subCategory);

        return "article_list";
    }


    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Market market = this.marketService.getMarket(id);
        model.addAttribute("market", market);
        return "market_detail";
    }

    @GetMapping("/create")
    public String marketCreate(MarketForm marketForm) {
        return "market_form";
    }

    @PostMapping("/create")
    public String marketCreate(@Valid MarketForm marketForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "market_form";
        }
        this.marketService.create(marketForm.getSubject(), marketForm.getContent(), marketForm.getPrice());
        return "redirect:/market/list"; // 질문 저장후 질문목록으로 이동
    }

}