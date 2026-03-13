package com.strucify.airBnb.dto.auth;


import com.strucify.airBnb.entity.enums.Role;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> role;
}
