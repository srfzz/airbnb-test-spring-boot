package com.strucify.airBnb.strategy;


import com.strucify.airBnb.entity.Inventory;

import java.math.BigDecimal;


public class OccupancyPricingStrategy implements PricingStrategy {
    private final PricingStrategy pricingStrategy;


    public OccupancyPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = pricingStrategy.calculatePrice(inventory);
        double ocuupancyRate = (double) inventory.getBookedCount() / inventory.getTotalCount();
        if (ocuupancyRate > 0.80) {
            price = price.multiply(BigDecimal.valueOf(1.2));
        }

        return price;
    }
}
