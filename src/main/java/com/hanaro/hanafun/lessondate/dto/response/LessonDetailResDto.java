package com.hanaro.hanafun.lessondate.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class LessonDetailResDto {
    private Long lessondateId;
    private LocalDate date;
    private Long lessonId;
}
