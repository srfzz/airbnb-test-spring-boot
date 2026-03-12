package com.strucify.airBnb.strategy;

import com.strucify.airBnb.entity.Inventory;

import java.math.BigDecimal;


public class SurgePriceStrategy implements PricingStrategy {
    private final PricingStrategy pricingStrategy;

    public SurgePriceStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return pricingStrategy.calculatePrice(inventory).multiply(inventory.getSurgeFactor());
    }
}
