package com.hanaro.hanafun.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    QR("QR"),
    PENDING("PENDING"),
    COMPLETE("COMPLETE");

    private String value;
}
