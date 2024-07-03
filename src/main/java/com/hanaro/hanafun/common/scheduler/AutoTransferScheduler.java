package com.hanaro.hanafun.common.scheduler;

import com.hanaro.hanafun.transaction.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoTransferScheduler {
    private final TransactionServiceImpl transactionService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") //매일 0시
    public void runAutoTransfer(){
        transactionService.autoTransfer();
    }
}
