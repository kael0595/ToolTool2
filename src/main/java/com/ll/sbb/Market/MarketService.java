package com.ll.sbb.Market;

import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.MarketAnswer.MarketAnswer;
import com.ll.sbb.User.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.core.annotation.MergedAnnotations.search;

@RequiredArgsConstructor
@Service
public class MarketService {
    private final MarketRepository marketRepository;

    public List<Market> getList() {
        return this.marketRepository.findAll();
    }

    private Specification<Market> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Market> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Market, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Market, MarketAnswer> a = q.join("marketAnswerList", JoinType.LEFT);
                Join<MarketAnswer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

    public Page<Market> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Market> spec = search(kw);
        return this.marketRepository.findAll(spec, pageable);
    }

    public Market getMarket(Integer id) {
        Optional<Market> Market = this.marketRepository.findById(id);
        if (Market.isPresent()) {
            return Market.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, Integer price, String brand, String type, String season, SiteUser user) {
        Market q = new Market();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setPrice(price);
        q.setBrand(brand);
        q.setType(type);
        q.setSeason(season);
        q.setAuthor(user);
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