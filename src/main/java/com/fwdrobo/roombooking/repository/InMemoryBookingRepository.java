package com.fwdrobo.roombooking.repository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.fwdrobo.roombooking.domain.Booking;
import com.fwdrobo.roombooking.domain.BookingWindowResult;
import com.fwdrobo.roombooking.service.BookingNotFoundException;
import com.fwdrobo.roombooking.service.InvalidBookingWindowException;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryBookingRepository {

    private final Map<String, Booking> bookings = new LinkedHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(3001);

    public InMemoryBookingRepository() {
        saveSeed(new Booking(
                "booking-1011",
                "room-101",
                LocalDateTime.parse("2030-01-15T09:00:00"),
                LocalDateTime.parse("2030-01-15T09:30:00")));
        saveSeed(new Booking(
                "booking-2021",
                "room-202",
                LocalDateTime.parse("2030-01-15T10:00:00"),
                LocalDateTime.parse("2030-01-15T10:30:00")));
        saveSeed(new Booking(
                "booking-2022",
                "room-202",
                LocalDateTime.parse("2030-01-15T12:00:00"),
                LocalDateTime.parse("2030-01-15T12:30:00")));
    }

    public synchronized Optional<Booking> findByIdAndRoomId(String bookingId, String roomId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null || !booking.roomId().equals(roomId)) {
            throw new BookingNotFoundException(roomId,bookingId);
        }
        return Optional.of(booking);
    }

    public synchronized List<Booking> findByRoomId(String roomId) {
        return bookings.values().stream()
                .filter(booking -> booking.roomId().equals(roomId))
                .toList();
    }

    public synchronized Booking create(String roomId, LocalDateTime start, LocalDateTime end) {
        String bookingId = "booking-" + nextId.getAndIncrement();
        Booking booking = new Booking(bookingId, roomId, start, end);
        bookings.put(bookingId, booking);
        return booking;
    }

    public synchronized long countByRoomId(String roomId) {
        return bookings.values().stream()
                .filter(booking -> booking.roomId().equals(roomId))
                .count();
    }

    private void saveSeed(Booking booking) {
        bookings.put(booking.id(), booking);
    }
}
