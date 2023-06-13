package com.ll.sbb.MarketAnswer;

import com.ll.sbb.Market.Market;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@RequiredArgsConstructor
@Service
public class MarketAnswerService {

    private final MarketAnswerRepository marketAnswerRepository;

    public void create(Market market, String content) {
        MarketAnswer m = new MarketAnswer();
        m.setContent(content);
        m.setCreateDate(LocalDateTime.now());
        m.setMarket(market);
        this.marketAnswerRepository.save(m);
    }
}