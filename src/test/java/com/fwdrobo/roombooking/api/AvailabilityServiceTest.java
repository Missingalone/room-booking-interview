package com.fwdrobo.roombooking.api;

import com.fwdrobo.roombooking.service.AvailabilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AvailabilityServiceTest {

    @Autowired
    private AvailabilityService availabilityService;

    @Test
    void test1(){
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 10, 15);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 10, 45);

        boolean available = availabilityService.isAvailable("room-202", start, end);
        System.out.println(available);
    }

}
