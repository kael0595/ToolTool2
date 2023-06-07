package com.ll.sbb.Answer;

import com.ll.sbb.Article.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerReposity answerReposity;


    public void create(Article article, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setArticle(article);
        this.answerReposity.save(answer);
    }
}