package com.hanaro.hanafun.reservation.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.reservation.dto.request.MyPageReqDto;
import com.hanaro.hanafun.reservation.dto.response.MyPageResDto;
import com.hanaro.hanafun.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservation/my")
    public ApiResponse<MyPageResDto> myPage(@RequestBody MyPageReqDto myPageReqDto) {
        return reservationService.myPage(myPageReqDto);
    }
}
