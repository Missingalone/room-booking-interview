package com.fwdrobo.roombooking.api;

import java.time.LocalDateTime;

import com.fwdrobo.roombooking.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms/{roomId}")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings/{bookingId}")
    public BookingResponse getBooking(
            @PathVariable String roomId,
            @PathVariable String bookingId
    ) {
        return BookingResponse.from(bookingService.get(roomId, bookingId));
    }

    @GetMapping("/availability")
    public AvailabilityResponse getAvailability(
            @PathVariable String roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        AvailabilityResponse availabilityResponse = new AvailabilityResponse(bookingService.isAvailable(roomId, start, end));
        return availabilityResponse;
    }
}
