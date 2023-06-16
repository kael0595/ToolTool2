package com.ll.sbb;

import com.ll.sbb.Article.ArticleService;
import com.ll.sbb.Market.MarketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MarketService marketService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.marketService.create(subject, content, 10000, null, null, null, null);
        }
    }
}
