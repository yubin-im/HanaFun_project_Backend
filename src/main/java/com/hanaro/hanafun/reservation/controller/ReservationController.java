package com.hanaro.hanafun.reservation.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.reservation.dto.request.MyPageReqDto;
import com.hanaro.hanafun.reservation.dto.response.MyPageResDto;
import com.hanaro.hanafun.reservation.dto.response.ReservationList;
import com.hanaro.hanafun.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    // 마이페이지 데이터 출력
    @GetMapping("/my")
    public ApiResponse<MyPageResDto> myPage(@RequestBody MyPageReqDto myPageReqDto) {
        return reservationService.myPage(myPageReqDto);
    }

    // 나의 신청 클래스 데이터 출력
    @GetMapping("/my/lessons")
    public ApiResponse<List<ReservationList>> myLessons(@RequestBody MyPageReqDto myPageReqDto) {
        return reservationService.myLessons(myPageReqDto);
    }
}
