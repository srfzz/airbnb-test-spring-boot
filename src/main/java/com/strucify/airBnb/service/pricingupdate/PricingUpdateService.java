package com.strucify.airBnb.service.pricingupdate;


import com.strucify.airBnb.entity.Hotel;
import com.strucify.airBnb.entity.HotelMinPrice;
import com.strucify.airBnb.entity.Inventory;
import com.strucify.airBnb.repository.HotelMinPriceRepository;
import com.strucify.airBnb.repository.HotelRepository;
import com.strucify.airBnb.repository.InventoryRepository;
import com.strucify.airBnb.strategy.PricingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Slf4j
@Service


public class PricingUpdateService {
    private final HotelRepository hotelRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final InventoryRepository inventoryRepository;
    private final PricingService pricingService;

    public PricingUpdateService(HotelRepository hotelRepository, HotelMinPriceRepository hotelMinPriceRepository, InventoryRepository inventoryRepository, PricingService pricingService) {
        this.hotelRepository = hotelRepository;
        this.hotelMinPriceRepository = hotelMinPriceRepository;
        this.inventoryRepository = inventoryRepository;
        this.pricingService = pricingService;
    }


    @Scheduled(cron = "* 0 0 * * *")
    @Transactional
    public void updatePrices() {
        log.info("Start PricingUpdateService.updatePrices");
        int pageNumber = 0;
        int pageSize = 100;
        while (true) {
            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(pageNumber, pageSize));
            if (hotelPage.isEmpty()) {
                break;
            }
            hotelPage.getContent().forEach(this::updateHotelPrices);
            pageNumber++;

        }
    }

    private void updateHotelPrices(Hotel hotel) {
        log.info("Start PricingUpdateService.updateHotelPrices");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusYears(1);
        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel, startDate, endDate);
        updateInventoryPrices(inventoryList);
        updateMinPrice(hotel, inventoryList, startDate, endDate);

    }

    private void updateMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
        log.info("Start PricingUpdateService.updateMinPrice");
        Map<LocalDate, BigDecimal> dailyMinPrices = inventoryList.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getDate,
                        Collectors.mapping(Inventory::getPrice,
                                Collectors.reducing(BigDecimal.valueOf(Double.MAX_VALUE), BinaryOperator.minBy(Comparator.naturalOrder())))
                ));

        List<HotelMinPrice> hotelPricesToSave = new ArrayList<>();
        dailyMinPrices.forEach((date, minPrice) -> {
            HotelMinPrice hotelMinPrice = hotelMinPriceRepository.findByHotelAndDate(hotel, date)
                    .orElse(HotelMinPrice.builder()
                            .hotel(hotel)
                            .date(date)
                            .build());

            hotelMinPrice.setPrice(minPrice);
            hotelPricesToSave.add(hotelMinPrice);
        });
        hotelMinPriceRepository.saveAll(hotelPricesToSave);
    }


    public void updateInventoryPrices(List<Inventory> inventoryList) {
        log.info("Start PricingUpdateService.updateInventoryPrices");
        inventoryList.forEach(item -> {

            BigDecimal dynamicPrice = pricingService.calculateDynamipricing(item);
            item.setPrice(dynamicPrice);
        });
        inventoryRepository.saveAll(inventoryList);
    }

}
