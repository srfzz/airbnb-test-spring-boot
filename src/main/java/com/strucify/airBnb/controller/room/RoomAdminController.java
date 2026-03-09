package com.strucify.airBnb.controller.room;

import com.strucify.airBnb.dto.roomDto.RoomDto;
import com.strucify.airBnb.service.room.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
public class RoomAdminController {
    private final RoomService roomService;

    public RoomAdminController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping()
    public ResponseEntity<RoomDto> addRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto) {
        log.info(roomDto.toString());
        RoomDto roomDtodata=roomService.createRoom(hotelId,roomDto);
        return ResponseEntity.ok().body(roomDtodata);

    }
    @GetMapping
        public ResponseEntity<List<RoomDto>> getAllRooms(@PathVariable Long hotelId) {
        List<RoomDto> rooms=roomService.gertAllRoomInHotel(hotelId);
        return ResponseEntity.ok().body(rooms);
        }
    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long roomId, @RequestBody RoomDto roomDto) {
        RoomDto room =roomService.updateRoomById(roomId,roomDto);
        return ResponseEntity.ok().body(room);

    }
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoomlById(roomId);
        return ResponseEntity.ok().build();
    }
}
