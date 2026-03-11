package com.strucify.airBnb.strategy;

import com.strucify.airBnb.entity.Inventory;

import java.math.BigDecimal;

public class BasePriceStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
