package com.strucify.airBnb.strategy;

import com.strucify.airBnb.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {


    public BigDecimal calculateDynamipricing(Inventory inventory) {
        PricingStrategy pricingStrategy = new BasePriceStrategy();
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new SurgePriceStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        return pricingStrategy.calculatePrice(inventory);

    }
}
