package com.ll.sbb.MarketAnswer;

import com.ll.sbb.Answer.Answer;
import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.Market.Market;
import com.ll.sbb.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Marker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MarketAnswerService {

    private final MarketAnswerRepository marketAnswerRepository;

    public void create(Market market, String content, SiteUser author) {
        MarketAnswer m = new MarketAnswer();
        m.setContent(content);
        m.setCreateDate(LocalDateTime.now());
        m.setMarket(market);
        m.setAuthor(author);
        this.marketAnswerRepository.save(m);
    }

    public void delete(MarketAnswer marketAnswer) {
        this.marketAnswerRepository.delete(marketAnswer);
    }

    public MarketAnswer getMarketAnswer(Integer id) {
        Optional<MarketAnswer> answer = this.marketAnswerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

}