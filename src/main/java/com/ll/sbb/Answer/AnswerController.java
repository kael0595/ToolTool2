package com.ll.sbb.Answer;

import com.ll.sbb.Article.Article;
import com.ll.sbb.Article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final ArticleService articleService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult) {
        Article article = this.articleService.getArticle(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("article", article);
            return "article_detail";
        }
        this.answerService.create(article, answerForm.getContent());
        return String.format("redirect:/article/detail/%s", id);
    }
}