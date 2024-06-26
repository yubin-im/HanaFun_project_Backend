package com.hanaro.hanafun.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrReqDto {
    private long depositId;
    private long withdrawId;
    private long lessondateId;
    private int payment;
}
