package com.strucify.airBnb.repository;

import com.strucify.airBnb.dto.report.HotelReportDto;
import com.strucify.airBnb.entity.Booking;
import com.strucify.airBnb.entity.Hotel;
import com.strucify.airBnb.entity.User;
import com.strucify.airBnb.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatusAndCreatedAtBefore(BookingStatus status, LocalDateTime createdAt);

    List<Booking> findBookingByHotel(Hotel hotel);

    List<Booking> findBookingByHotelAndCreatedAtBetween(Hotel hotel, LocalDateTime createdAt, LocalDateTime createdAt2);

    @Query("SELECT new com.strucify.airBnb.dto.report.HotelReportDto(" +
            "COUNT(b), " +
            "CAST(COALESCE(SUM(b.amount), 0) AS BigDecimal), " +
            "CAST(COALESCE(AVG(b.amount), 0) AS BigDecimal)) " +
            "FROM Booking b " +
            "WHERE b.hotel.id = :hotelId " +
            "AND b.createdAt BETWEEN :startDate AND :endDate " +
            "AND b.status = 'CONFIRMED'")
    HotelReportDto getHotelReportData(Long hotelId, LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findBookingByUser(User databaseUser);
}