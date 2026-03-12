package com.strucify.airBnb.service;


import com.strucify.airBnb.entity.User;
import com.strucify.airBnb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Bad Credentials"));
    }

    public User getUserById(Long id) {
        User userDetails = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userDetails;
    }
}
