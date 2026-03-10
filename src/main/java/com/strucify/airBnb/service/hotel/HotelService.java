package com.strucify.airBnb.service.hotel;

import com.strucify.airBnb.controller.hotel.HotelInfoDto;
import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.entity.Hotel;

public interface HotelService {

    Hoteldto createHotel(Hoteldto hoteldto);
    Hoteldto getByHotelId(Long hotelId);
    Hoteldto updateHotelById(Long id,Hoteldto hoteldto);
    void deleteHotelById(Long id);
    Hoteldto partialUpdateHotelById(Long id,Hoteldto hoteldto);
    void setActiveHotel(Long hotelId);
    HotelInfoDto getHotelInfo(Long hotelId);


}
