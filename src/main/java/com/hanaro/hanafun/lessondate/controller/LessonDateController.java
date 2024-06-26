package com.hanaro.hanafun.lessondate.controller;

import com.hanaro.hanafun.lessondate.service.LessonDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LessonDateController {
    private final LessonDateService lessonDateService;

}
