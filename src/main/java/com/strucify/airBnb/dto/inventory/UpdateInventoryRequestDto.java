package com.strucify.airBnb.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateInventoryRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal surgefactor;
    private Boolean closed;
}
