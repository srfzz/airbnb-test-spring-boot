package com.strucify.airBnb.service;

import com.strucify.airBnb.dto.auth.LoginReponseDto;
import com.strucify.airBnb.dto.auth.LoginReponseToUserDto;
import com.strucify.airBnb.dto.auth.LoginRequestDto;
import com.strucify.airBnb.dto.auth.SignupRequestDto;
import com.strucify.airBnb.dto.user.UserDto;
import com.strucify.airBnb.entity.User;
import com.strucify.airBnb.entity.enums.Role;
import com.strucify.airBnb.exceptions.ResourceAlreadyExistsException;
import com.strucify.airBnb.exceptions.ResourceNotFoundException;
import com.strucify.airBnb.repository.UserRepository;
import com.strucify.airBnb.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtService jwtService, ModelMapper modelMapper, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional(readOnly = true)
    public LoginReponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequestDto.getEmail(),
                                loginRequestDto.getPassword()));
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAcessToken(user);
        String refreshToken = jwtService.generateAcessToken(user);

        return LoginReponseDto.builder().accessToken(accessToken).refreshToken(refreshToken).userDetails(modelMapper.map(user, UserDto.class)).build();

    }


    @Transactional
    public UserDto register(SignupRequestDto signupRequestDto) {
        log.info("signupRequestDto={}", signupRequestDto);
        Boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if (exists) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }
        User newUser = modelMapper.map(signupRequestDto, User.class);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (signupRequestDto.getRole() != null && !signupRequestDto.getRole().isEmpty()) {
            newUser.setRole(signupRequestDto.getRole());
        } else {
            newUser.setRole(Set.of(Role.ROLE_GUEST));
        }
        User savedUser = userRepository.save(newUser);
        return modelMapper.map(savedUser, UserDto.class);

    }

    public LoginReponseToUserDto refreshToken(String refreshToken) {
        Long id = jwtService.getUserId(refreshToken);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String accessToken = jwtService.generateAcessToken(user);
        return LoginReponseToUserDto.builder()
                .token(accessToken)
                .userDetails(modelMapper.map(user, UserDto.class))
                .build();
    }
}
