package com.strucify.airBnb.controller.publicApi;

import com.strucify.airBnb.dto.HotelSearch.HotelSearchRequestDto;
import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.service.inventory.InventoryService;
import com.strucify.airBnb.service.room.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelBrowseController {
    private final InventoryService inventoryService;
    private final RoomService roomService;

    public HotelBrowseController(InventoryService inventoryService, RoomService roomService) {
        this.inventoryService = inventoryService;
        this.roomService = roomService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Hoteldto>> searchHotel(@RequestBody HotelSearchRequestDto hotelSearchRequestDto) {


        Page<Hoteldto> page=inventoryService.searchHotels(hotelSearchRequestDto);
        return ResponseEntity.ok(page);

    }

}
