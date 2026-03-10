package com.strucify.airBnb.controller.publicApi;

import com.strucify.airBnb.controller.hotel.HotelInfoDto;
import com.strucify.airBnb.dto.HotelSearch.HotelSearchRequestDto;
import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.dto.roomDto.RoomDto;
import com.strucify.airBnb.service.hotel.HotelService;
import com.strucify.airBnb.service.inventory.InventoryService;
import com.strucify.airBnb.service.room.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    public ResponseEntity<Page<Hoteldto>> searchHotel(@RequestBody HotelSearchRequestDto hotelSearchRequestDto) {


        Page<Hoteldto> page=inventoryService.searchHotels(hotelSearchRequestDto);
        return ResponseEntity.ok(page);

    }


    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable("hotelId") Long hotelId) {
        HotelInfoDto hotelInfoDto=hotelService.getHotelInfo(hotelId);
        return ResponseEntity.ok(hotelInfoDto);
    }

}
