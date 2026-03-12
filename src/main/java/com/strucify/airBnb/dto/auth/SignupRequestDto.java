package com.strucify.airBnb.dto.auth;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
}
