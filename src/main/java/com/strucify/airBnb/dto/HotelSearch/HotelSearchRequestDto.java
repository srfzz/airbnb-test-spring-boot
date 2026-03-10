package com.strucify.airBnb.dto.HotelSearch;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelSearchRequestDto {
    private String  city;
    private LocalDate startDate;
    private LocalDate endDate;
    private  Integer roomCount;
    private Integer size=10;
    private Integer page=0;
}
