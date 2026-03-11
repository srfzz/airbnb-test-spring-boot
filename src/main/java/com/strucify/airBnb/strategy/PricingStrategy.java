package com.strucify.airBnb.strategy;

import com.strucify.airBnb.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
