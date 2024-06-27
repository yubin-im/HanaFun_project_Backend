package com.hanaro.hanafun.reservation.dto.request;

import lombok.Getter;

@Getter
public class BookLessonReqDto {
    private Long lessondateId;
    private Long userId;
    private int applicant;  // 예약 수량
    private Long accountId;
    private String password;  // 계좌 비밀번호 확인
}
