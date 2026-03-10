package com.strucify.airBnb.dto.guests;
import com.strucify.airBnb.entity.User;
import com.strucify.airBnb.entity.enums.Gender;
import lombok.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private  Integer age;
}
