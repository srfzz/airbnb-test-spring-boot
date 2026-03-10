package com.strucify.airBnb.controller.booking;


import com.strucify.airBnb.dto.booking.BookingDto;
import com.strucify.airBnb.dto.booking.BookingRequestDto;
import com.strucify.airBnb.dto.guests.GuestDto;
import com.strucify.airBnb.service.booking.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping("/init")
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequestDto bookingRequestDto) {

        return ResponseEntity.ok(bookingService.initailzeBooking(bookingRequestDto));
    }

    @PostMapping("/{bookingId}/addguests")
    public ResponseEntity<BookingDto> addGuests(@RequestBody List<GuestDto> guestDto, @PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDto));
    }
}
