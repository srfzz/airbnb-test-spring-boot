package com.strucify.airBnb.service.room;


import ch.qos.logback.core.model.Model;
import com.strucify.airBnb.dto.roomDto.RoomDto;
import com.strucify.airBnb.entity.Hotel;
import com.strucify.airBnb.entity.Room;
import com.strucify.airBnb.exceptions.ResourceNotFoundException;
import com.strucify.airBnb.repository.HotelRepository;
import com.strucify.airBnb.repository.RoomRepository;
import com.strucify.airBnb.service.inventory.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;

    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper, HotelRepository hotelRepository, InventoryService inventoryService) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.hotelRepository = hotelRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    @Transactional
    public RoomDto createRoom(Long hotelId,RoomDto roomDto) {
        log.info("RoomServiceImpl createRoom");
        log.info(roomDto.toString());
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundException("Hotel not found"));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
       Room savedRoom = roomRepository.save(room);
        if(hotel.getActive())
        {
            inventoryService.initializeInventoryIfMissing(savedRoom);
        }
       //Todo add a invetory once hotel is active
       return modelMapper.map(savedRoom, RoomDto.class);
    }

    @Override
    @Transactional
    public List<RoomDto> gertAllRoomInHotel(Long hotelId) {
       log.info("RoomServiceImpl gertAllRoomInHotel");

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundException("Hotel not found"));
        if(hotel.getRooms()==null){
            throw new ResourceNotFoundException("Throw No rooms in This hotel");
        }
        List<Room> rooms =roomRepository.getRoomsByHotelId(hotelId);
        return rooms.stream().map(r->modelMapper.map(r,RoomDto.class)).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public RoomDto getByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    @Transactional
    public RoomDto updateRoomById(Long id, RoomDto roomDto) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
       modelMapper.map(room,roomDto);
       roomRepository.save(room);
        return modelMapper.map(room,RoomDto.class);

    }

    @Override
    @Transactional
    public void deleteRoomlById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
        inventoryService.deleteFutureInventory(id);
        roomRepository.delete(room);
    }

    @Override
    @Transactional
    public RoomDto partialUpdateRoomById(Long id, RoomDto roomDto) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Room not found"));

        modelMapper.map(room,roomDto);
        roomRepository.save(room);
        return modelMapper.map(room,RoomDto.class);
    }

//    @Override
//    public void setActiveRoom(Long roomId) {
//        Room room = roomRepository.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
//
//
//    }
}
