package com.hanaro.hanafun.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BasicErrorStatus {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "NO_ACCESS_AUTH"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN");

    private final HttpStatus httpStatus;
    private final String message;
}