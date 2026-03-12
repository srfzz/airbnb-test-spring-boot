package com.strucify.airBnb.service;


import com.strucify.airBnb.entity.User;
import com.strucify.airBnb.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public User getUserById(Long id) {
        User userDetails = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userDetails;
    }
}
