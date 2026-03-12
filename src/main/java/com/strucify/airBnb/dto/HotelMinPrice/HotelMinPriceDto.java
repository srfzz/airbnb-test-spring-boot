package com.strucify.airBnb.dto.HotelMinPrice;

import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.entity.Hotel;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelMinPriceDto {
    private Hoteldto hoteldto;
    private BigDecimal price;

    // SENIOR MOVE: This constructor allows the Repository to work
    // without causing an infinite loop.
    public HotelMinPriceDto(Hotel hotel, Double avgPrice) {
        // Manually map only the fields you need to avoid the loop
        this.hoteldto = Hoteldto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .city(hotel.getCity())
                .photos(hotel.getPhotos())
                .amenities(hotel.getAmenities())
                .active(hotel.getActive())
                .contactInfo(hotel.getContactInfo())
                
                .build();

        this.price = (avgPrice != null) ? BigDecimal.valueOf(avgPrice) : BigDecimal.ZERO;
    }
}