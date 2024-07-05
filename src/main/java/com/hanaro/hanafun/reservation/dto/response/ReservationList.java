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
public class ReservationList {
    private Long reservationId;
    private Long lessondateId;
    private Long lessonId;
    private String image;
    private String title;
    private String location;
    private LocalDate date;
    private String categoryName;
}
