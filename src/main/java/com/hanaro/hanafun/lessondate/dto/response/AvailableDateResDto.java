package com.hanaro.hanafun.lessondate.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class AvailableDateResDto {
    private Long lessondateId;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int quantityLeft;  // 잔여수량 (모집인원 - 신청인원)
}
