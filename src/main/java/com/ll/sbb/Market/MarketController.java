package com.ll.sbb.Market;


import com.ll.sbb.Article.Article;
import com.ll.sbb.Article.ArticleForm;
import com.ll.sbb.Category.subCategory;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String marketModify(MarketForm marketForm, @PathVariable("id") Integer id, Principal principal) {
        Market market = this.marketService.getMarket(id);
        if (!market.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        marketForm.setSubject(market.getSubject());
        marketForm.setContent(market.getContent());
        marketForm.setPrice(market.getPrice());
        marketForm.setType(market.getType());
        marketForm.setSeason(market.getSeason());
        marketForm.setBrand(market.getBrand());
        return "market_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String marketModify(@Valid MarketForm marketForm, BindingResult bindingResult,
                               Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "market_form";
        }
        Market market = this.marketService.getMarket(id);
        if (!market.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.marketService.modify(market, marketForm.getSubject(), marketForm.getContent(), marketForm.getPrice(), marketForm.getBrand(), marketForm.getSeason(), marketForm.getType());
        return String.format("redirect:/market/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String marketDelete(Principal principal, @PathVariable("id") Integer id) {
        Market market = this.marketService.getMarket(id);
        if (!market.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.marketService.delete(market);
        return "redirect:/";
    }

    @PostMapping("/vote/{id}")
    @ResponseBody
    public int marketVote(Principal principal, @PathVariable("id") Integer id) {
        if (principal == null) {
            return -1;
        } else {
            Market market = this.marketService.getMarket(id);
            SiteUser siteUser = this.userService.getUser(principal.getName());

            int tempLikeCount = market.getLikeCount();

            if (market.getVoter().isEmpty()) {
                market.setLikeCount(tempLikeCount + 1);
                this.marketService.vote(market, siteUser);
            } else {
                for (SiteUser voter : market.getVoter()) {
                    if (voter.getId() == siteUser.getId()) {
                        market.setLikeCount(tempLikeCount - 1);
                        this.marketService.delVote(market, siteUser);
                    }
                }
            }

            // => Join Count(*)
            Market upadateMarket = this.marketService.getMarket(id);
            //
            return upadateMarket.getLikeCount();
        }
    }
}