package com.hanaro.hanafun.lessondate.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class LessonDateNotFoundException extends CustomException {
    static String MESSAGE = "LESSONDATE_NOT_FOUND";

    public LessonDateNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
