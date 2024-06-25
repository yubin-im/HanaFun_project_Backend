package com.hanaro.hanafun.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        BasicErrorStatus error = BasicErrorStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<Void> response = new ApiResponse<>(false, error.getCode(), error.getMessage(), null);
        return new ResponseEntity<>(response, error.getHttpStatus());
    }
}
