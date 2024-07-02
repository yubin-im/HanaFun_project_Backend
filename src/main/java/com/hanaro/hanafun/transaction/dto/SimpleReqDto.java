package com.hanaro.hanafun.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleReqDto {
    private long withdrawId;
    private long lessondateId;
    private long reservationId;
    private int payment;
    private int point;
}
