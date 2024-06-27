package com.hanaro.hanafun.transaction.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.transaction.dto.PayResDto;
import com.hanaro.hanafun.transaction.dto.QrReqDto;
import com.hanaro.hanafun.transaction.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @PostMapping("/qr")
    public ResponseEntity<ApiResponse> qrPay(@RequestBody QrReqDto qrReqDto){
        PayResDto payResDto = transactionService.qrPay(qrReqDto);
        return ResponseEntity.ok(new ApiResponse(true, "ok", payResDto));
    }
}
