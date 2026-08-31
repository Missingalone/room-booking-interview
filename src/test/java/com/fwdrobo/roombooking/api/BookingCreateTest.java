package com.fwdrobo.roombooking.api;

import com.fwdrobo.roombooking.domain.Booking;
import com.fwdrobo.roombooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class BookingCreateTest {

    @Autowired
    private BookingService bookingService;

    @Test
    void testCreate1(){
        //预期正常
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 10, 30);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 11, 0);
        Booking booking = bookingService.create("room-202", start, end);
        System.out.println(booking);
    }
    @Test
    void testCreate2(){

        //预期报错
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 6, 30);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 8, 31);
        Booking booking = bookingService.create("room-202", start, end);
        System.out.println(booking);
    }
    @Test
    void testCreate3(){
        //预期错误
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 10, 30);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 12, 31);
        Booking booking = bookingService.create("room-202", start, end);
        System.out.println(booking);
    }
    @Test
    void testCreate4(){
        //预期正常
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 6, 30);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 8, 30);
        Booking booking = bookingService.create("room-202", start, end);
        System.out.println(booking);
    }
    @Test
    void testCreate5(){
        //预期错误
        LocalDateTime start = null;
        LocalDateTime end = null;
        Booking booking = bookingService.create("room-202", start, end);
        System.out.println(booking);
    }

}
