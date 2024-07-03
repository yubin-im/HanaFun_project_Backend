package com.hanaro.hanafun.lesson.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
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
