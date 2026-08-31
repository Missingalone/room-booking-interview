package com.fwdrobo.roombooking.api;

import com.fwdrobo.roombooking.domain.BookingWindowPolicy;
import com.fwdrobo.roombooking.domain.BookingWindowResult;
import com.fwdrobo.roombooking.service.InvalidBookingWindowException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class BookingWindowPolicyTest {

    @Autowired
    private BookingWindowPolicy bookingWindowPolicy;

    /**
     * 30 分钟和 120 分钟均为合法边界
     */

    //测试29
    @Test
    void test1(){
        //预期报错 DURATION_OUT_OF_RANGE
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 10, 31);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 11, 0);
        BookingWindowResult evaluate = bookingWindowPolicy.evaluate(start, end);
        if(!evaluate.equals(BookingWindowResult.VALID)){
            throw new InvalidBookingWindowException(evaluate);
        }
    }
    //测试30
    @Test
    void test2(){
        //预期正常
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 10, 30);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 11, 0);
        BookingWindowResult evaluate = bookingWindowPolicy.evaluate(start, end);
        if(!evaluate.equals(BookingWindowResult.VALID)){
            throw new InvalidBookingWindowException(evaluate);
        }
    }
    //测试120
    @Test
    void test3(){
        //预期正常
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 10, 30);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 12, 30);
        BookingWindowResult evaluate = bookingWindowPolicy.evaluate(start, end);
        if(!evaluate.equals(BookingWindowResult.VALID)){
            throw new InvalidBookingWindowException(evaluate);
        }
    }
    //测试121
    @Test
    void test4(){
        //预期报错 DURATION_OUT_OF_RANGE
        LocalDateTime start = LocalDateTime.of(2030, 1, 15, 10, 30);
        LocalDateTime end = LocalDateTime.of(2030, 1, 15, 12, 31);
        BookingWindowResult evaluate = bookingWindowPolicy.evaluate(start, end);
        if(!evaluate.equals(BookingWindowResult.VALID)){
            throw new InvalidBookingWindowException(evaluate);
        }
    }
    //测试 违反多项规则
    @Test
    void tes5(){
        //预期报错 MISSING_BOUNDARY 因为窗口规则就是 MISSING_BOUNDARY>END_NOT_AFTER_START>DURATION_OUT_OF_RANGE>VALID
        LocalDateTime start = null;
        LocalDateTime end =null;
        BookingWindowResult evaluate = bookingWindowPolicy.evaluate(start, end);
        if(!evaluate.equals(BookingWindowResult.VALID)){
            throw new InvalidBookingWindowException(evaluate);
        }
    }
}
