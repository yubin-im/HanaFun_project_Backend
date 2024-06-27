package com.hanaro.hanafun.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationPerson {
    private LocalDateTime startTime;
    private String userName;
    private String email;
}
