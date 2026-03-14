package com.strucify.airBnb.service.userprofileservice;


import com.strucify.airBnb.dto.userprofile.UpdateUserProfileDto;
import com.strucify.airBnb.entity.User;
import com.strucify.airBnb.exceptions.ResourceNotFoundException;
import com.strucify.airBnb.repository.UserRepository;
import com.strucify.airBnb.util.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserProfileServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    @Modifying
    public void updateProfile(UpdateUserProfileDto updateUserProfileDto) {
        User sessionUser = SecurityUtils.getCurrentuser();
        User databaseUser = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        modelMapper.map(updateUserProfileDto, databaseUser);
        userRepository.save(databaseUser);

    }
}
