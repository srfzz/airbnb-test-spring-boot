package com.strucify.airBnb.controller.hotel;

import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.service.hotel.HotelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/hotels")
public class HotelController {

    private final HotelServiceImpl hotelService;

    public HotelController(HotelServiceImpl hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping()
    public ResponseEntity<Hoteldto> addHotel(@RequestBody Hoteldto hotel){
        log.info("Adding hotel {}", hotel);
      Hoteldto hoteldto =hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hoteldto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hoteldto> getHotelById(@PathVariable Long id){
        log.info("Getting hotel by hotelId {}", id);

        return ResponseEntity.ok().body(hotelService.getByHotelId(id));
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<Hoteldto> updateHotelById(@PathVariable Long hotelId, @RequestBody Hoteldto hotel){
        log.info("Updating hotel {}", hotel);
        Hoteldto hoteldto =hotelService.updateHotelById(hotelId,hotel);
        return ResponseEntity.ok().body(hoteldto);
    }

    @PatchMapping("/{hotelId}")
    public ResponseEntity<Hoteldto> partialUpdateHotelById(@PathVariable Long hotelId, @RequestBody Hoteldto hotel){
        hotelService.partialUpdateHotelById(hotelId,hotel);
        log.info("Patching hotel ID {}: updates {}", hotelId, hotel);
        return ResponseEntity.ok().body(hotelService.getByHotelId(hotelId));
    }

    @DeleteMapping("/hotelId")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId){
        log.info("Deleting hotel by hotelId {}", hotelId);
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{hotelId}/status")
    public ResponseEntity<Void> toggleHotelActiveStatus(@PathVariable Long hotelId)
    {
        hotelService.setActiveHotel(hotelId);
        log.info("Toggle hotel active status {}", hotelId);
        return ResponseEntity.ok().build();
    }
}
