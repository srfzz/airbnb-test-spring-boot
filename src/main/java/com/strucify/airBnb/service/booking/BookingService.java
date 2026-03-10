package com.strucify.airBnb.service.booking;

import com.strucify.airBnb.dto.booking.BookingDto;
import com.strucify.airBnb.dto.booking.BookingRequestDto;
import com.strucify.airBnb.dto.guests.GuestDto;

import java.util.List;

public interface BookingService {

    BookingDto initailzeBooking(BookingRequestDto bookingRequestDto);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDto);
}
