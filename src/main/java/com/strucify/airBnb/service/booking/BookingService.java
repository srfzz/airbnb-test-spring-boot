package com.strucify.airBnb.service.booking;

import com.strucify.airBnb.dto.booking.BookingDto;
import com.strucify.airBnb.dto.booking.BookingRequestDto;
import com.strucify.airBnb.dto.guests.GuestDto;
import com.strucify.airBnb.dto.report.HotelReportDto;
import com.strucify.airBnb.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    BookingDto initailzeBooking(BookingRequestDto bookingRequestDto);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDto);

    void cancelExpiredBooking(Booking booking);

    List<BookingDto> getAllBookingByHotel(Long hotelId);

    HotelReportDto fetchReports(Long hotelId, LocalDateTime startDate, LocalDateTime endDate);

    List<BookingDto> getAllBookingByOwner();
}
