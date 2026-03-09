package com.strucify.airBnb.controller.room;

import com.strucify.airBnb.dto.roomDto.RoomDto;
import com.strucify.airBnb.service.room.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
public class RoomAdminController {
    private final RoomService roomService;

    public RoomAdminController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping()
    public ResponseEntity<RoomDto> addRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto) {

    }
}
