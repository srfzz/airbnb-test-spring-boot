package com.strucify.airBnb.repository;


import com.strucify.airBnb.dto.HotelMinPrice.HotelMinPriceDto;
import com.strucify.airBnb.entity.Hotel;
import com.strucify.airBnb.entity.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice, Long> {
    @Query("""
            select new com.strucify.airBnb.dto.HotelMinPrice.HotelMinPriceDto(i.hotel,Avg(i.price)) from HotelMinPrice i where i.hotel.city=:city  and i.date >= :startDate AND i.date < :endDate and i.hotel.active=true group by i.hotel having count(i.id) =:daysCount
            """)
    Page<HotelMinPriceDto> findAvailableHotels(@Param("city") String city, @Param("roomCount") Integer roomCount, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("daysCount") Long daysCount, Pageable pageable);

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
