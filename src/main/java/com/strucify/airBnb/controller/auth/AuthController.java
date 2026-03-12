package com.strucify.airBnb.controller.auth;

import com.strucify.airBnb.dto.auth.LoginReponseDto;
import com.strucify.airBnb.dto.auth.LoginReponseToUserDto;
import com.strucify.airBnb.dto.auth.LoginRequestDto;
import com.strucify.airBnb.dto.auth.SignupRequestDto;
import com.strucify.airBnb.dto.user.UserDto;
import com.strucify.airBnb.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignupRequestDto signupRequestDto) {
        UserDto userDto = authService.register(signupRequestDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginReponseToUserDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        LoginReponseDto loginReponseDto = authService.login(loginRequestDto);
        Cookie cookie = new Cookie("refreshToken", loginReponseDto.getRefreshToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);//nmeed rto be try on https on in production
        cookie.setMaxAge(30 * 24 * 60 * 60);
        httpServletResponse.addCookie(cookie);
        LoginReponseToUserDto loginReponseToUserDto = LoginReponseToUserDto.builder().userDetails(loginReponseDto.getUserDetails()).token(loginReponseDto.getAccessToken()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(loginReponseToUserDto);

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginReponseToUserDto> refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new AuthenticationCredentialsNotFoundException("refresh cookie not found (no Cookie Sent");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Refresh token not found"));


        LoginReponseToUserDto responseBody = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> refresh(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .build();
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }
}
