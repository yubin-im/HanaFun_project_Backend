package com.hanaro.hanafun.lesson.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.lesson.dto.request.OpenedLessonsReqDto;
import com.hanaro.hanafun.lesson.dto.response.LessonInfoResDto;
import com.hanaro.hanafun.lesson.dto.response.OpenedLessonsResDto;
import com.hanaro.hanafun.lesson.service.LessonService;
import com.hanaro.hanafun.lessondate.dto.response.LessonDetailResDto;
import com.hanaro.hanafun.lessondate.service.LessonDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final LessonDateService lessonDateService;

    // 개설 클래스 관리- 개설 클래스 목록 출력
    @GetMapping("/reservation/my/opened")
    public ResponseEntity<ApiResponse> openedLessons(@RequestBody OpenedLessonsReqDto openedLessonsReqDto) {
        List<OpenedLessonsResDto> openedLessons = lessonService.openedLessons(openedLessonsReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", openedLessons));
    }

    // 개설 클래스 상세
    @GetMapping("/reservation/my/opened/{lessonId}")
    public ResponseEntity<ApiResponse> lessonDetail(@PathVariable Long lessonId) {
        List<LessonDetailResDto> lessonDetails = lessonDateService.lessonDetail(lessonId);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", lessonDetails));
    }

    // 클래스 상세보기
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<ApiResponse> lessonInfo(@PathVariable Long lessonId) {
        LessonInfoResDto lessonInfoResDto = lessonService.lessonInfo(lessonId);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", lessonInfoResDto));
    }
}
