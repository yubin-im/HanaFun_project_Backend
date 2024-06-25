package com.hanaro.hanafun.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BasicErrorStatus {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "E001", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E002", "인증이 필요합니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "E003", "값을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E004", "서버 에러"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "E005", "금지된 작업입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
