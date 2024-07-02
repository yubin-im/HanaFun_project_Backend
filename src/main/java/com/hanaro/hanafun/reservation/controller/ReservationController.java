package com.hanaro.hanafun.reservation.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.reservation.dto.request.*;
import com.hanaro.hanafun.reservation.dto.response.*;
import com.hanaro.hanafun.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    // 마이페이지 데이터 출력
    @GetMapping("/my")
    public ResponseEntity<ApiResponse> myPage(@RequestBody MyPageReqDto myPageReqDto) {
        MyPageResDto myPageResDto = reservationService.myPage(myPageReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", myPageResDto));
    }

    // 나의 신청 클래스 데이터 출력
    @GetMapping("/my/lessons")
    public ResponseEntity<ApiResponse> myLessons(@RequestBody MyPageReqDto myPageReqDto) {
        List<ReservationList> lessons = reservationService.myLessons(myPageReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", lessons));
    }

    // 신청 클래스 일정 데이터 출력
    @GetMapping("/my/schedule")
    public ResponseEntity<ApiResponse> mySchedules(@RequestBody MyScheduleReqDto myScheduleReqDto) {
        List<MyScheduleResDto> mySchedules = reservationService.mySchedules(myScheduleReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", mySchedules));
    }

    // 개설 클래스 상세- 강좌날짜 별 예약자 정보 출력
    @PostMapping("/my/opened/people")
    public ResponseEntity<ApiResponse> lessonDateDetail(@RequestBody LessonDateDetailReqDto lessonDateDetailReqDto) {
        LessonDateDetailResDto lessonDateDetailResDto = reservationService.lessonDateDetail(lessonDateDetailReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", lessonDateDetailResDto));
    }

    // 클래스 예약하기 (결제 제외)
    @PostMapping("/check")
    public ResponseEntity<ApiResponse> bookLesson(@RequestBody BookLessonReqDto bookLessonReqDto) {
        BookLessonResDto bookLessonResDto = reservationService.bookLesson(bookLessonReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", bookLessonResDto));
    }

    // 클래스 취소하기 (환불 제외)
    @PostMapping("/cancel")
    public void cancelLesson(@RequestBody CancelLessonReqDto cancelLessonReqDto) {
        reservationService.cancelLesson(cancelLessonReqDto);
    }
}