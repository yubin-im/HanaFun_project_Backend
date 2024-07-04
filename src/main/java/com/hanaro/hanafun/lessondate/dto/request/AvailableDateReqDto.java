package com.hanaro.hanafun.lessondate.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AvailableDateReqDto {
    private Long lessonId;
}
