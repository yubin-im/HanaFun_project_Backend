package com.hanaro.hanafun.category.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends CustomException {
    static String MESSAGE = "CATEGORY_NOT_FOUND";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
