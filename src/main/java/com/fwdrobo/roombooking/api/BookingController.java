package com.fwdrobo.roombooking.api;

import java.net.URI;
import java.time.LocalDateTime;

import com.fwdrobo.roombooking.domain.Booking;
import com.fwdrobo.roombooking.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> createBooking(
            @RequestParam @PathVariable String roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        Booking booking = bookingService.create(roomId, start, end);
        //这里使用了ai去查询location的语法结构
        URI location = URI.create("/bookings " + booking.id());
        return ResponseEntity.created(location).body(BookingResponse.from(booking));
    }
}
