package com.hanaro.hanafun.lesson.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenedLessonsResDto {
    private Long lessonId;
    private String image;
    private String title;
}
