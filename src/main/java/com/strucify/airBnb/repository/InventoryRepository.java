package com.strucify.airBnb.repository;

import com.strucify.airBnb.dto.HotelSearch.HotelSearchRequestDto;
import com.strucify.airBnb.entity.Hotel;
import com.strucify.airBnb.entity.Inventory;
import com.strucify.airBnb.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
select i.hotel from Inventory i where i.city=:city and (i.totalCount - i.bookedCount ) >=:roomCount and i.date between :startDate and :endDate group by i.hotel,i.room having count(i.id) =:daysCount
""")
    Page<Hotel>  findAvailableHotels(@Param("city") String city, @Param("roomCount") Integer roomCount, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("daysCount") Long daysCount, Pageable pageable);
}



