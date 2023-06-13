package com.ll.sbb.MarketAnswer;

import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor

public class MarketAnswerController {
    private final MarketService marketService;

    private final MarketAnswerService marketAnswerService;

    @PostMapping("marketAnswer/create/{id}")
    public String createMarketAnswer(Model model, @PathVariable("id") Integer id, @Valid MarketAnswerForm marketAnswerForm,
                                     BindingResult bindingResult) {
        Market market = this.marketService.getMarket(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("market", market);
            return "market_detail";
        }
        this.marketAnswerService.create(market, marketAnswerForm.getContent());
        return String.format("redirect:/market/detail/%s", id);
    }
}