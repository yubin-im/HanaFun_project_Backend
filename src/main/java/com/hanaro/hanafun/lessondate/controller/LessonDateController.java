package com.hanaro.hanafun.lessondate.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.lessondate.dto.request.AvailableDateReqDto;
import com.hanaro.hanafun.lessondate.dto.response.AvailableDateResDto;
import com.hanaro.hanafun.lessondate.service.LessonDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonDateController {
    private final LessonDateService lessonDateService;

    // 클래스 예약 가능 날짜 출력
    @GetMapping("/lesson/date-select")
    public ResponseEntity<ApiResponse> availableDate(@RequestBody AvailableDateReqDto availableDateReqDto) {
        List<AvailableDateResDto> availableDateResDtoList = lessonDateService.availableDate(availableDateReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", availableDateResDtoList));
    }
}
