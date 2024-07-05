package com.hanaro.hanafun.lesson.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreateLessonReqDto {
    private Long categoryId;
    private String title;
    private String location;
    private int price;
    private int capacity;
    private String image;
    private String description;
    private String materials;
    private List<CreateLessonDateReqDto> lessonDate;
}
