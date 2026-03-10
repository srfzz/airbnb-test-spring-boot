package com.strucify.airBnb.controller.hotel;

import com.strucify.airBnb.dto.hotelDto.Hoteldto;
import com.strucify.airBnb.dto.roomDto.RoomDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelInfoDto {
    private Hoteldto hotel;
    private List<RoomDto> rooms;
}
