package com.strucify.airBnb.repository;

import com.strucify.airBnb.entity.Inventory;
import com.strucify.airBnb.entity.Room;
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
}



