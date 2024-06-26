package com.hanaro.hanafun.lesson.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.lesson.dto.request.OpenedLessonsReqDto;
import com.hanaro.hanafun.lesson.dto.response.OpenedLessonsResDto;
import com.hanaro.hanafun.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    // 개설 클래스 관리- 개설 클래스 목록 출력
    @GetMapping("/reservation/my/opened")
    public ApiResponse<List<OpenedLessonsResDto>> openedLessons(@RequestBody OpenedLessonsReqDto openedLessonsReqDto) {
        return lessonService.openedLessons(openedLessonsReqDto);
    }
}
