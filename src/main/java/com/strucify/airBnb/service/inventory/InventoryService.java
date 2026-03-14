package com.strucify.airBnb.service.inventory;

import com.strucify.airBnb.dto.HotelMinPrice.HotelMinPriceDto;
import com.strucify.airBnb.dto.HotelSearch.HotelSearchRequestDto;
import com.strucify.airBnb.dto.inventory.InventoryDto;
import com.strucify.airBnb.dto.inventory.UpdateInventoryRequestDto;
import com.strucify.airBnb.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    void initializeRoomsForAYear(Room room);

    void initializeInventoryIfMissing(Room room);


    void deleteFutureInventory(Long roomId);

    Page<HotelMinPriceDto> searchHotels(HotelSearchRequestDto hotelSearchRequestDto);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
