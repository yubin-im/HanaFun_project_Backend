package com.hanaro.hanafun.reservation.service;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.reservation.dto.request.MyPageReqDto;
import com.hanaro.hanafun.reservation.dto.request.MyScheduleReqDto;
import com.hanaro.hanafun.reservation.dto.request.LessonDateDetailReqDto;
import com.hanaro.hanafun.reservation.dto.response.MyPageResDto;
import com.hanaro.hanafun.reservation.dto.response.MyScheduleResDto;
import com.hanaro.hanafun.reservation.dto.response.ReservationList;
import com.hanaro.hanafun.reservation.dto.response.LessonDateDetailResDto;

import java.util.List;

public interface ReservationService {
    // 마이페이지 데이터 출력
    ApiResponse<MyPageResDto> myPage(MyPageReqDto myPageReqDto);

    // 나의 신청 클래스 데이터 출력
    ApiResponse<List<ReservationList>> myLessons(MyPageReqDto myPageReqDto);

    // 신청 클래스 일정 데이터 출력
    ApiResponse<List<MyScheduleResDto>> mySchedules(MyScheduleReqDto myScheduleReqDto);

    // 개설 클래스 상세- 강좌날짜 별 예약자 정보 출력
    ApiResponse<LessonDateDetailResDto> lessonDateDetail(LessonDateDetailReqDto lessonDateDetailReqDto);
}
