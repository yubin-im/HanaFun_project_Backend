package com.hanaro.hanafun.transaction.service;

import com.hanaro.hanafun.transaction.dto.PayResDto;
import com.hanaro.hanafun.transaction.dto.QrReqDto;

public interface TransactionService {
    PayResDto qrPay(QrReqDto qrReqDto);
}
