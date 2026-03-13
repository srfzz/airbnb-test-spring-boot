package com.strucify.airBnb.service.booking;

import com.strucify.airBnb.dto.booking.BookingDto;
import com.strucify.airBnb.dto.booking.BookingRequestDto;
import com.strucify.airBnb.dto.guests.GuestDto;
import com.strucify.airBnb.dto.report.HotelReportDto;
import com.strucify.airBnb.entity.*;
import com.strucify.airBnb.entity.enums.BookingStatus;
import com.strucify.airBnb.exceptions.ResourceNotFoundException;
import com.strucify.airBnb.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, HotelRepository hotelRepository, RoomRepository roomRepository, InventoryRepository inventoryRepository, ModelMapper modelMapper, GuestRepository guestRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.inventoryRepository = inventoryRepository;
        this.modelMapper = modelMapper;
        this.guestRepository = guestRepository;
    }

    @Override
    @Transactional
    public BookingDto initailzeBooking(BookingRequestDto bookingRequestDto) {
        log.info("BookingServiceImpl initailzeBooking");
        Hotel hotel = hotelRepository.findById(bookingRequestDto.getHotelId()).orElseThrow(() -> new ResourceNotFoundException("Hotel not found id:" + bookingRequestDto.getHotelId()));
        Room room = roomRepository.findById(bookingRequestDto.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room not found id:" + bookingRequestDto.getRoomId()));
        List<Inventory> inventories = inventoryRepository.findAndLockAvailableInventory(bookingRequestDto.getHotelId(), bookingRequestDto.getRoomId(), bookingRequestDto.getCheckInDate(), bookingRequestDto.getCheckOutDate(), bookingRequestDto.getRoomsCount());
        Long daysCount = ChronoUnit.DAYS.between(bookingRequestDto.getCheckInDate(), bookingRequestDto.getCheckOutDate());
        if (inventories.size() != daysCount) {
            throw new IllegalStateException("Rooms Are Not Available any More !");

        }
        for (Inventory inventory : inventories) {
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequestDto.getRoomsCount());
        }
        inventoryRepository.saveAll(inventories);


        Booking booking = Booking.builder()
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .status(BookingStatus.RESERVED)
                .roomsCount(inventories.size())
                .user(returncurrentUser())
                .amount(BigDecimal.ZERO)
                .build();
        bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDto) {
        log.info("BookingServiceImpl addGuests");
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found id:" + bookingId));
        if (hasBookingExpired(booking)) {
            throw new IllegalStateException("Booking has expired !");
        }
        if (booking.getStatus() != BookingStatus.RESERVED) {
            throw new IllegalStateException("Booking is not Under State ,Cannot Add Guests  !");
        }

        List<Guest> guests = guestDto.stream().map(
                dto -> {
                    Guest guest = modelMapper.map(dto, Guest.class);
                    guest.setUser(returncurrentUser());
                    return guest;
                }
        ).toList();
        List<Guest> savedGuests = guestRepository.saveAll(guests);
        booking.getGuests().addAll(savedGuests);
        bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public void cancelExpiredBooking(Booking booking) {
        log.info("Cancelling expired booking id: {}", booking.getId());
        booking.setStatus(BookingStatus.EXPIRED);
        bookingRepository.save(booking);
        List<Inventory> inventories = inventoryRepository.findAndLockAvailableInventory(
                booking.getHotel().getId(),
                booking.getRoom().getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                0
        );


        inventories.forEach(inventory -> {
            int updatedReservedCount = inventory.getReservedCount() - booking.getRoomsCount();
            inventory.setReservedCount(Math.max(0, updatedReservedCount)); // Safety check to never go below 0
        });

        inventoryRepository.saveAll(inventories);
        log.info("Inventory freed for expired booking id: {}", booking.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("@hotelSecurity.isOwner(#hotelId)")
    public List<BookingDto> getAllBookingByHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found id:" + hotelId));
        List<Booking> bookings = bookingRepository.findBookingByHotel(hotel);
        return bookings.stream().map(booking -> modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("@hotelSecurity.isOwner(#hotelId)")
    public HotelReportDto fetchReports(Long hotelId, LocalDateTime startDate, LocalDateTime endDate) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found id:" + hotelId));

        return bookingRepository.getHotelReportData(hotelId, startDate, endDate);
    }

    public boolean hasBookingExpired(Booking booking) {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User returncurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }
}
