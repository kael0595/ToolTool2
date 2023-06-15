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

    }
}
