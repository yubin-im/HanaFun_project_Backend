package com.hanaro.hanafun.lessondate.service;

import com.hanaro.hanafun.lessondate.dto.request.AvailableDateReqDto;
import com.hanaro.hanafun.lessondate.dto.response.AvailableDateResDto;
import com.hanaro.hanafun.lessondate.dto.response.LessonDetailResDto;

import java.util.List;

public interface LessonDateService {
    // 개설 클래스 상세
    List<LessonDetailResDto> lessonDetail(Long lessonId);

    // 클래스 예약 가능 날짜 출력
    List<AvailableDateResDto> availableDate(AvailableDateReqDto availableDateReqDto);
}
