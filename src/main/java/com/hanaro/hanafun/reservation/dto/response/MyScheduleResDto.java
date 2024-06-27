package com.hanaro.hanafun.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MyScheduleResDto {
    private Long reservationId;
    private Long lessonId;
    private LocalDate date;
    private String title;
}
