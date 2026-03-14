package com.strucify.airBnb.controller.userprofile;


import com.strucify.airBnb.dto.booking.BookingDto;
import com.strucify.airBnb.dto.userprofile.UpdateUserProfileDto;
import com.strucify.airBnb.service.booking.BookingServiceImpl;
import com.strucify.airBnb.service.userprofileservice.UserProfileServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/profile")
public class UserProfile {

    private final UserProfileServiceImpl userProfileService;
    private final BookingServiceImpl bookingService;

    public UserProfile(UserProfileServiceImpl userProfileService, BookingServiceImpl bookingService) {
        this.userProfileService = userProfileService;
        this.bookingService = bookingService;
    }

    @PutMapping()
    public ResponseEntity<Void> updateRoomStatus(@RequestBody UpdateUserProfileDto updateUserProfileDto) {
        userProfileService.updateProfile(updateUserProfileDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myBookings")
    public ResponseEntity<List<BookingDto>> getMyBookings() {
        return ResponseEntity.ok().body(bookingService.getAllBookingByOwner());
    }
}
