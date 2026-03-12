package com.strucify.airBnb.dto.auth;

import com.strucify.airBnb.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginReponseDto {
    private String accessToken;
    private UserDto userDetails;
    private String refreshToken;
}
