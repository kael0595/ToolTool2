package com.ll.sbb.Article;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRange {

    private int minPrice;
    private int maxPrice;

    public PriceRange(int minPrice, int maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMin() {
        return minPrice;
    }

    public int getMax() {
        return maxPrice;
    }
}
