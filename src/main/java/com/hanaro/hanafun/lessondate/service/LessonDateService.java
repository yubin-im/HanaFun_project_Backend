package com.hanaro.hanafun.lessondate.service;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.lessondate.dto.response.LessonDetailResDto;

import java.util.List;

public interface LessonDateService {
    // 개설 클래스 상세
    ApiResponse<List<LessonDetailResDto>> lessonDetail(Long lessonId);
}
