package com.strucify.airBnb.dto.hotelDto;

import com.strucify.airBnb.entity.HotelContactInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hoteldto {
    private Long id;
    private String name;
    private String city;
    private Long ownerId;
    private List<String> photos;
    private List<String> amenities;
    private Boolean active;
    private HotelContactInfo contactInfo;

}
