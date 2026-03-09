package com.strucify.airBnb.dto.hotelDto;

import com.strucify.airBnb.entity.Booking;
import com.strucify.airBnb.entity.HotelContactInfo;
import com.strucify.airBnb.entity.Inventory;
import com.strucify.airBnb.entity.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Hoteldto {
    private Long id;
    private String name;
    private String city;
    private List<String> photos;
    private List<String> amenities;
    private Boolean active;
    private HotelContactInfo contactInfo;

}
