package com.ll.sbb.MarketAnswer;

import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor

public class MarketAnswerController {
    private final MarketService marketService;

    private final MarketAnswerService marketAnswerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("marketAnswer/create/{id}")
    public String createMarketAnswer(Model model, @PathVariable("id") Integer id, @Valid MarketAnswerForm marketAnswerForm,
                                     BindingResult bindingResult, Principal principal) {
        Market market = this.marketService.getMarket(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("market", market);
            return "market_detail";
        }
        this.marketAnswerService.create(market, marketAnswerForm.getContent(), siteUser);
        return String.format("redirect:/market/detail/%s", id);
    }
}