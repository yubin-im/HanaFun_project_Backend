package com.hanaro.hanafun.lesson.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonInfoResDto {
    private Long lessonId;
    private String image;
    private String title;
    private int price;
    private String description;
    private String location;
    private String materials;
    private int capacity;
    private String categoryName;
    private boolean isHostMe;
}
