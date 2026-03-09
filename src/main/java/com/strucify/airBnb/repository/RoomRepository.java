package com.strucify.airBnb.repository;

import com.strucify.airBnb.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("select r from Room r Join FETCH r.hotel where r.hotel.id =:hotelId")
    List<Room> getRoomsByHotelId(@Param("hotelId") Long hotelId);
}
