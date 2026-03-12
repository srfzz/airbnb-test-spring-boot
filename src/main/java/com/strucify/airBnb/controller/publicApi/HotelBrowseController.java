package com.strucify.airBnb.controller.publicApi;

import com.strucify.airBnb.controller.hotel.HotelInfoDto;
import com.strucify.airBnb.dto.HotelMinPrice.HotelMinPriceDto;
import com.strucify.airBnb.dto.HotelSearch.HotelSearchRequestDto;
import com.strucify.airBnb.service.hotel.HotelService;
import com.strucify.airBnb.service.inventory.InventoryService;
import com.strucify.airBnb.service.room.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
public class HotelBrowseController {
    private final InventoryService inventoryService;
    private final RoomService roomService;
    private final HotelService hotelService;

    public HotelBrowseController(InventoryService inventoryService, RoomService roomService, HotelService hotelService) {
        this.inventoryService = inventoryService;
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HotelMinPriceDto>> searchHotel(@RequestBody HotelSearchRequestDto hotelSearchRequestDto) {


        Page<HotelMinPriceDto> page = inventoryService.searchHotels(hotelSearchRequestDto);
        return ResponseEntity.ok(page);

    }


    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable("hotelId") Long hotelId) {
        HotelInfoDto hotelInfoDto = hotelService.getHotelInfo(hotelId);
        return ResponseEntity.ok(hotelInfoDto);
    }

}
