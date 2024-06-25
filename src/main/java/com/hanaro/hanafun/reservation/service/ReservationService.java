package com.hanaro.hanafun.reservation.service;

import com.hanaro.hanafun.reservation.dto.request.MyPageReqDto;
import com.hanaro.hanafun.reservation.dto.response.MyPageResDto;

public interface ReservationService {
    MyPageResDto myPage(MyPageReqDto myPageReqDto);
}
