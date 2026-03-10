package com.strucify.airBnb.service.booking;

import com.strucify.airBnb.entity.Booking;
import com.strucify.airBnb.entity.enums.BookingStatus;
import com.strucify.airBnb.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingCleanupTask {
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void cleanupExpiredBookings() {
        log.info("Cleaning up expired bookings");
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(10);

        List<Booking> expiredBookings = bookingRepository
                .findByStatusAndCreatedAtBefore(BookingStatus.RESERVED, cutoff);

        expiredBookings.forEach(bookingService::cancelExpiredBooking);
    }
}
