package com.ll.sbb.MarketAnswer;

import com.ll.sbb.Answer.Answer;
import com.ll.sbb.Answer.AnswerForm;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketService;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/marketAnswer")
@Controller
@RequiredArgsConstructor
public class MarketAnswerController {
    private final MarketService marketService;

    private final MarketAnswerService marketAnswerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String marketAnswerDelete(Principal principal, @PathVariable("id") Integer id) {
        MarketAnswer marketAnswer = this.marketAnswerService.getMarketAnswer(id);
        if (!marketAnswer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.marketAnswerService.delete(marketAnswer);
        return String.format("redirect:/market/detail/%s", marketAnswer.getMarket().getId());
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String marketAnswerModify(@Valid MarketAnswerForm marketAnswerForm, BindingResult bindingResult,
                                     @PathVariable("id") Integer id, Principal principal) {
        MarketAnswer marketAnswer = this.marketAnswerService.getMarketAnswer(id);
        if (bindingResult.hasErrors()) {
            return String.format("redirect:/market/detail/%s", marketAnswer.getMarket().getId());
        }
        if (!marketAnswer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.marketAnswerService.modify(marketAnswer, marketAnswerForm.getContent());
        return String.format("redirect:/market/detail/%s", marketAnswer.getMarket().getId());
    }
}