package com.strucify.airBnb.service.room;

import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.dto.roomDto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createRoom(Long hotelId,RoomDto roomDto);
    List<RoomDto> gertAllRoomInHotel(Long hotelId);
    RoomDto getByRoomId(Long roomId);
    RoomDto updateRoomById(Long id,RoomDto roomDto);
    void deleteRoomlById(Long id);
    RoomDto partialUpdateRoomById(Long id,RoomDto roomDto);
//    void setActiveRoom(Long roomId);


}
