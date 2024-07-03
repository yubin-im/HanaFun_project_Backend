package com.hanaro.hanafun.common.scheduler;

import com.hanaro.hanafun.transaction.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoTransferScheduler {
    private final TransactionServiceImpl transactionService;

    private static final Logger logger = LoggerFactory.getLogger(AutoTransferScheduler.class);

    @Scheduled(cron = "0 15 13 * * ?") //매일 0시
    public void runAutoTransfer(){
        logger.info("auto transfer scheduling test started!!!!!!!!!!!!!!");
        transactionService.autoTransfer();
    }
}
