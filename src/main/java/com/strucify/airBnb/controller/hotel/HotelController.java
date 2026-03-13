package com.strucify.airBnb.controller.hotel;

import com.strucify.airBnb.dto.booking.BookingDto;
import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.dto.report.HotelReportDto;
import com.strucify.airBnb.service.booking.BookingService;
import com.strucify.airBnb.service.hotel.HotelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/hotels")
public class HotelController {

    private final HotelServiceImpl hotelService;
    private final BookingService bookingService;

    public HotelController(HotelServiceImpl hotelService, BookingService bookingService) {
        this.hotelService = hotelService;
        this.bookingService = bookingService;
    }

    @PostMapping()
    public ResponseEntity<Hoteldto> addHotel(@RequestBody Hoteldto hotel) {
        log.info("Adding hotel {}", hotel);
        Hoteldto hoteldto = hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hoteldto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hoteldto> getHotelById(@PathVariable Long id) {
        log.info("Getting hotel by hotelId {}", id);

        return ResponseEntity.ok().body(hotelService.getByHotelId(id));
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<Hoteldto> updateHotelById(@PathVariable Long hotelId, @RequestBody Hoteldto hotel) {
        log.info("Updating hotel {}", hotel);
        Hoteldto hoteldto = hotelService.updateHotelById(hotelId, hotel);
        return ResponseEntity.ok().body(hoteldto);
    }

    @PatchMapping("/{hotelId}")
    public ResponseEntity<Hoteldto> partialUpdateHotelById(@PathVariable Long hotelId, @RequestBody Hoteldto hotel) {
        hotelService.partialUpdateHotelById(hotelId, hotel);
        log.info("Patching hotel ID {}: updates {}", hotelId, hotel);
        return ResponseEntity.ok().body(hotelService.getByHotelId(hotelId));
    }

    @DeleteMapping("/hotelId")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId) {
        log.info("Deleting hotel by hotelId {}", hotelId);
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{hotelId}/status")
    public ResponseEntity<Void> toggleHotelActiveStatus(@PathVariable Long hotelId) {
        hotelService.setActiveHotel(hotelId);
        log.info("Toggle hotel active status {}", hotelId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<Hoteldto>> getALlHotels() {
        return ResponseEntity.ok().body(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}/booking")
    public ResponseEntity<List<BookingDto>> getBookingsByHotelId(@PathVariable Long hotelId) {
        return ResponseEntity.ok().body(bookingService.getAllBookingByHotel(hotelId));
    }

    @GetMapping("/{hotelId}/reports")
    public ResponseEntity<HotelReportDto> hotelreports(@PathVariable Long hotelId, @RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
    }
}
