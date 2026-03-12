package com.strucify.airBnb.util;

import com.strucify.airBnb.entity.User;
import com.strucify.airBnb.repository.HotelRepository;
import org.springframework.stereotype.Component;

@Component("hotelSecurity")
public class HotelSecurityExecutor {

    private final HotelRepository hotelRepository;

    public HotelSecurityExecutor(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public boolean isOwner(Long hotelId) {
        User currentUser = SecurityUtils.getCurrentuser();
        return hotelRepository.findById(hotelId)
                .map(hotel -> hotel.getOwner().getId().equals(currentUser.getId()))
                .orElse(false);
    }
}