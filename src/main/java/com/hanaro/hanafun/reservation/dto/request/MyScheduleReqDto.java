package com.hanaro.hanafun.reservation.dto.request;

import lombok.Getter;

@Getter
public class MyScheduleReqDto {
    private Long userId;
    private int year;
    private int month;
}
