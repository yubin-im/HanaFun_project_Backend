package com.hanaro.hanafun.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReservationList {
    private Long reservationId;
    private Long lessondateId;
    private Long lessonId;
    private String image;
    private String title;
    private String location;
    private LocalDate date;
}
