package com.hanaro.hanafun.transaction.service.impl;

import com.hanaro.hanafun.transaction.domain.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl {
    private final TransactionRepository transactionRepository;
}
