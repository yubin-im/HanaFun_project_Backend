package com.hanaro.hanafun.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CancelLessonResDto {
    private boolean isSuccess;
    private String message;
}
