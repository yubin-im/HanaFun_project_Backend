package com.hanaro.hanafun.reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookLessonReqDto {
    private Long lessondateId;
    private int applicant;  // 예약 수량
    private Long accountId;
    private String password;  // 계좌 비밀번호 확인
}
