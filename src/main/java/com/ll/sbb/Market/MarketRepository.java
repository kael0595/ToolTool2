package com.ll.sbb.Market;

import com.ll.sbb.Market.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Integer> {
    List<Market> findByMainCategory(String mainCategory);

    List<Market> findByType(String type);

    List<Market> findBySeason(String season);
}