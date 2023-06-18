package com.ll.sbb.Market;

import com.ll.sbb.Article.Article;
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
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Market> spec = search(kw);
        return this.marketRepository.findAll(spec, pageable);
    }

    public Market getMarket(Integer id) {
        Optional<Market> Market = this.marketRepository.findById(id);
        if (Market.isPresent()) {
            return Market.get();
        } else {
            throw new DataNotFoundException("market not found");
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


    //////////////////////////0618 추가

    public Page<Market> getPriceList(int page, String kw, int min, int max) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Market> spec = (root, query, builder) -> {
            return builder.between(root.get("price"), min, max);
        };
        // 비트윈으로 미니멈, 맥시멈값 지정후 spec변수에 담고 해당변수값의 아티클만 파인드올로 불러옴  //
        return this.marketRepository.findAll(spec, pageable);
    }

    public List<Market> getByPrice(int min, int max) {

        return this.marketRepository.findByPriceBetween(min, max);
        // 비트윈을 사용하면 인트값의 범위를 지정하여 불러올 수 있다. (프라이스가 int값으로 지정되어있음 )
    }

    public Page<Market> getSeasonList(int page, String kw, String season) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Market> spec = searchSeason(season);
        return this.marketRepository.findAll(spec, pageable);
    }

    public Specification<Market> searchSeason(String sea) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Season 컬럼을 기준으로 검색 조건 생성
            if (sea != null) {
                Path<String> seasonPath = root.get("season");
                Predicate seasonPredicate = criteriaBuilder.equal(seasonPath, sea);
                predicates.add(seasonPredicate);
            }

            // 다른 조건들을 추가하고 싶다면 여기에 추가

            // 검색 조건들을 조합하여 최종 검색 조건 생성
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public List<Market> getBySeason(String season) {

        return this.marketRepository.findBySeason(season);
    }

    public List<Market> getByType(String type) {

        return this.marketRepository.findByType(type);
    }

    public Page<Market> getTypeList(int page, String kw, String type) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Market> spec = searchType(type);
        return this.marketRepository.findAll(spec, pageable);
    }

    public Specification<Market> searchType(String sea) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Season 컬럼을 기준으로 검색 조건 생성
            if (sea != null) {
                Path<String> typePath = root.get("type");
                Predicate typePredicate = criteriaBuilder.equal(typePath, sea);
                predicates.add(typePredicate);
            }

            // 다른 조건들을 추가하고 싶다면 여기에 추가

            // 검색 조건들을 조합하여 최종 검색 조건 생성
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}