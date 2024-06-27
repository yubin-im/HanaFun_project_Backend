package com.hanaro.hanafun.lesson.service;

import com.hanaro.hanafun.lesson.dto.request.OpenedLessonsReqDto;
import com.hanaro.hanafun.lesson.dto.response.LessonInfoResDto;
import com.hanaro.hanafun.lesson.dto.response.OpenedLessonsResDto;

import java.util.List;

public interface LessonService {
    // 개설 클래스 관리- 개설 클래스 목록 출력
    List<OpenedLessonsResDto> openedLessons(OpenedLessonsReqDto openedLessonsReqDto);

    // 클래스 상세보기
    LessonInfoResDto lessonInfo(Long lessonId);
}
