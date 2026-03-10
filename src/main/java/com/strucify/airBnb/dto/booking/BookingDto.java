package com.strucify.airBnb.dto.booking;

import com.strucify.airBnb.dto.guests.GuestDto;
import com.strucify.airBnb.entity.enums.BookingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

    private Long id;

    private BigDecimal amount;

    private BookingStatus status;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Set<GuestDto> guests;
    private LocalDateTime createdAt;
}
