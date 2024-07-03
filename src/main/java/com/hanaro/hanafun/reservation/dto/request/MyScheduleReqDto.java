package com.hanaro.hanafun.reservation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyScheduleReqDto {
    private int year;
    private int month;
}
