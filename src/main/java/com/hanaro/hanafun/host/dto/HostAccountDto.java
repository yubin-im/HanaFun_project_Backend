package com.hanaro.hanafun.host.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HostAccountDto {
    private long accountId;
    private String accountName;
    private String accountNumber;
    private int balance;
}
