package com.hanaro.hanafun.transaction.service;

import com.hanaro.hanafun.transaction.dto.PayResDto;
import com.hanaro.hanafun.transaction.dto.PaybackReqDto;
import com.hanaro.hanafun.transaction.dto.QrReqDto;
import com.hanaro.hanafun.transaction.dto.SimpleReqDto;

public interface TransactionService {
    PayResDto qrPay(QrReqDto qrReqDto);
    PayResDto simplePay(Long userId, SimpleReqDto simpleReqDto);
    PayResDto payback(Long userId, PaybackReqDto paybackReqDto);
}
