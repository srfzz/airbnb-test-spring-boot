package com.strucify.airBnb.dto.auth;

import com.strucify.airBnb.dto.user.UserDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginReponseToUserDto {
    private String token;
    private UserDto userDetails;
}
