package com.ll.sbb.Market;

import com.ll.sbb.Article.Article;
import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MarketService {
    private final MarketRepository marketRepository;

    public List<Market> getList() {
        return this.marketRepository.findAll();
    }

    public Market getMarket(Integer id) {
        Optional<Market> Market = this.marketRepository.findById(id);
        if (Market.isPresent()) {
            return Market.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, Integer price) {
        Market q = new Market();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setPrice(price);
        this.marketRepository.save(q);
    }


    public List<Market> getMainCategory(String mainCategory) {
        return this.marketRepository.findByMainCategory(mainCategory);
    }

    public List<Market> findByType(String type) {
        return this.marketRepository.findByType(type);
    }

    public List<Market> getAll() {
        return this.marketRepository.findAll();
    }


    public List<Market> findBySeason(String season) {
        return this.marketRepository.findBySeason(season);
    }

}