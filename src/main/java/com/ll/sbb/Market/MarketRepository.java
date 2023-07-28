package com.ll.sbb.Market;

import com.ll.sbb.User.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Integer> {
    List<Market> findByType(String type);

    List<Market> findBySeason(String season);

    Page<Market> findAll(Specification<Market> spec, Pageable pageable);

    List<Market> findByPriceBetween(int min, int max);

    List<Market> findByAuthor(SiteUser siteUser);
}