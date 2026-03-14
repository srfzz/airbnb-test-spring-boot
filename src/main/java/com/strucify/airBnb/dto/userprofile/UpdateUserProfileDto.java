package com.strucify.airBnb.dto.userprofile;


import com.strucify.airBnb.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserProfileDto {
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
}
