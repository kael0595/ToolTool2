package com.ll.sbb;

import com.ll.sbb.Review.ArticleService;
import com.ll.sbb.Market.MarketService;
import com.ll.sbb.Notice.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private NoticeService noticeService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.noticeService.create(subject, content);
        }
    }
}
