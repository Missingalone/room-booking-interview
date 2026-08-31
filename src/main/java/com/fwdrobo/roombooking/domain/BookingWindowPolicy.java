package com.fwdrobo.roombooking.domain;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fwdrobo.roombooking.service.InvalidBookingWindowException;
import org.springframework.stereotype.Component;

@Component
public class BookingWindowPolicy {

    public BookingWindowResult evaluate(LocalDateTime start, LocalDateTime end) {
        /**
         * start 或 end 缺失：MISSING_BOUNDARY
         * end 不晚于 start：END_NOT_AFTER_START
         * 时长短于 30 分钟或长于 120 分钟：DURATION_OUT_OF_RANGE
         * 其它情况：VALID
         */

        //start end 缺失
        if (start == null || end == null) {
            throw new InvalidBookingWindowException(BookingWindowResult.MISSING_BOUNDARY);
        }

        //end 不晚于start
        if (end.isBefore(start)) {
            throw new InvalidBookingWindowException(BookingWindowResult.END_NOT_AFTER_START);
        }
        //时长合法性
        long minutes = Duration.between(start, end).toMinutes();
        if (minutes < 30 || minutes > 120) {
            throw new InvalidBookingWindowException(BookingWindowResult.DURATION_OUT_OF_RANGE);
        }
        //其他情况
        return BookingWindowResult.VALID;
    }
}
