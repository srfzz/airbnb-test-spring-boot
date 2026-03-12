package com.strucify.airBnb.service.inventory;

import com.strucify.airBnb.dto.HotelMinPrice.HotelMinPriceDto;
import com.strucify.airBnb.dto.HotelSearch.HotelSearchRequestDto;
import com.strucify.airBnb.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomsForAYear(Room room);

    void initializeInventoryIfMissing(Room room);


    void deleteFutureInventory(Long roomId);

    Page<HotelMinPriceDto> searchHotels(HotelSearchRequestDto hotelSearchRequestDto);
}
