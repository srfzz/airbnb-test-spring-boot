package com.strucify.airBnb.repository;

import com.strucify.airBnb.entity.Hotel;
import com.strucify.airBnb.entity.Inventory;
import com.strucify.airBnb.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findFirstByRoomOrderByDateDesc(Room room);

    @Modifying
    @Query("DELETE FROM Inventory i WHERE i.room.id = :roomId AND i.date >= :date")
    void deleteFutureInventory(@Param("roomId") Long roomId, @Param("date") LocalDate date);

    @Query("""
            select i.hotel from Inventory i where i.city=:city and (i.totalCount - i.bookedCount - i.reservedCount ) >=:roomCount and i.date >= :startDate AND i.date < :endDate and i.closed=false group by i.hotel,i.room having count(i.id) =:daysCount
            """)
    Page<Hotel> findAvailableHotels(@Param("city") String city, @Param("roomCount") Integer roomCount, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("daysCount") Long daysCount, Pageable pageable);


    @Query("""
            select i from Inventory i where i.room.id=:roomId and i.hotel.id=:hotelId 
            and i.closed=false
             and i.date >= :startDate AND i.date < :endDate
             and  (i.totalCount - i.bookedCount - i.reservedCount ) >=:roomsCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    List<Inventory> findAndLockAvailableInventory(
            @Param("hotelId") Long hotelId,
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount);


    @Query("""
            select i from Inventory i where i.room.id=:roomId 
             and i.date >= :startDate AND i.date < :endDate
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    List<Inventory> findAndLockAvailableInventoryByRoom(

            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


    List<Inventory> findByHotelAndDateBetween(Hotel hotel, LocalDate startDate, LocalDate endDate);

    List<Inventory> findByRoom(Room room);

    List<Inventory> findByRoomOrderByDateDesc(Room room);


    @Modifying
    @Query("""
                        update Inventory i set i.surgeFactor=:surgeFactor,i.closed=:closed
                        where i.room.id=:roomId
                        and i.date between :startDate and :endDate
            
                        and i.closed=false
            """)
    void updateInventory(@Param("surgeFactor") BigDecimal surgeFactor,
                         @Param("closed") Boolean closed,
                         @Param("roomId") Long roomId,
                         @Param("startDate") LocalDate startDate,
                         @Param("endDate") LocalDate endDate);

}



