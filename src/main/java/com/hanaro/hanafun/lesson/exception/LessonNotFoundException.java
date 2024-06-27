package com.hanaro.hanafun.lesson.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class LessonNotFoundException extends CustomException {
    static String MESSAGE = "LESSON_NOT_FOUND";

    public LessonNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
