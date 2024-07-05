package com.hanaro.hanafun.lesson.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FullLessonResDto {
    private Long lessonId;
    private String image;
    private String title;
    private int price;
    private String hostName;
    private int applicantSum;
}
