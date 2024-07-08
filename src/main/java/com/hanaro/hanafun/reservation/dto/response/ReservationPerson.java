package com.hanaro.hanafun.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPerson {
    private LocalDateTime startTime;
    private String userName;
    private String email;
    private int applicant;
}
