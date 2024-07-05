package com.hanaro.hanafun.lessondate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDetailResDto {
    private Long lessondateId;
    private LocalDate date;
    private Long lessonId;
    private String title;
}
