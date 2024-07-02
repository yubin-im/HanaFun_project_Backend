package com.hanaro.hanafun.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyPageResDto {
    private int point;
    private List<ReservationList> lessons;
}