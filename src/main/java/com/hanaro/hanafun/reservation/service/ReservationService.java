package com.hanaro.hanafun.reservation.service;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.reservation.dto.request.MyPageReqDto;
import com.hanaro.hanafun.reservation.dto.response.MyPageResDto;
import com.hanaro.hanafun.reservation.dto.response.ReservationList;

import java.util.List;

public interface ReservationService {
    // 마이페이지 데이터 출력
    ApiResponse<MyPageResDto> myPage(MyPageReqDto myPageReqDto);

    // 나의 신청 클래스 데이터 출력
    ApiResponse<List<ReservationList>> myLessons(MyPageReqDto myPageReqDto);
}
