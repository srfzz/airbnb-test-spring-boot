package com.strucify.airBnb.dto.roomDto;
import com.strucify.airBnb.entity.Hotel;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomDto {
    private Long id;
    private String type;
    private BigDecimal basePrice;
    private List<String> photos;
    private List<String> amenities;
    private Integer totalCount;
    private Integer capacity;


}
