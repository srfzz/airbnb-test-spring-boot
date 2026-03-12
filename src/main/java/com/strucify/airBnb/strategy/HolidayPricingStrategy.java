package com.strucify.airBnb.strategy;


import com.strucify.airBnb.entity.Inventory;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;


@Slf4j
public class HolidayPricingStrategy implements PricingStrategy {
    private final PricingStrategy pricingStrategy;

    public HolidayPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = pricingStrategy.calculatePrice(inventory);
        Boolean isRTodayHoliday = true;//we are going to call an api to check if its holiday
        if (isRTodayHoliday) {
            price = price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }
}
