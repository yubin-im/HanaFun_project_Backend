package com.hanaro.hanafun.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyScheduleResDto {
    private Long reservationId;
    private Long lessonId;
    private LocalDate date;
    private String title;
}
