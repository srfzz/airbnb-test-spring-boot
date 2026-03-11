package com.strucify.airBnb.strategy;

import com.strucify.airBnb.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class UrgencyPricingStrategy implements PricingStrategy {
    private final PricingStrategy pricingStrategy;

    public UrgencyPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    private boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = pricingStrategy.calculatePrice(inventory);
        if (isWeekday(inventory.getDate())) {
            return price.multiply(BigDecimal.valueOf(1.15));
        }
        return price;
    }
}
