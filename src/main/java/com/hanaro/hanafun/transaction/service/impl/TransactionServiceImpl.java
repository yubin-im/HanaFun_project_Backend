package com.hanaro.hanafun.transaction.service.impl;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import com.hanaro.hanafun.account.exception.AccountNotFoundException;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.reservation.domain.ReservationRepository;
import com.hanaro.hanafun.revenue.domain.RevenueEntity;
import com.hanaro.hanafun.revenue.domain.RevenueRepository;
import com.hanaro.hanafun.transaction.domain.TransactionEntity;
import com.hanaro.hanafun.transaction.domain.TransactionRepository;
import com.hanaro.hanafun.transaction.dto.PayResDto;
import com.hanaro.hanafun.transaction.dto.QrReqDto;
import com.hanaro.hanafun.transaction.enums.Type;
import com.hanaro.hanafun.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final LessonRepository lessonRepository;
    private final LessonDateRepository lessonDateRepository;
    private final ReservationRepository reservationRepository;
    private final RevenueRepository revenueRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public PayResDto qrPay(QrReqDto qrReqDto) {
        //계좌 잔액 업데이트
        AccountEntity withdrawAccount = calcWithdraw(qrReqDto.getWithdrawId(), qrReqDto.getPayment(), "qr");
        AccountEntity depositAccount = calcDeposit(qrReqDto.getDepositId(), qrReqDto.getPayment(), "qr");

        //매출 변경 업데이트
        calcRevenue(qrReqDto.getLessonId(), qrReqDto.getPayment());

        //거래 내역 저장
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .depositAccount(depositAccount)
                .withdrawAccount(withdrawAccount)
                .reservationEntity(reservationRepository
                        .findByUserEntityUserIdAndLessonDateEntityLessondateId(qrReqDto.getGuestId(), qrReqDto.getLessondateId())
                        .get())
                .payment(qrReqDto.getPayment())
                .point(0)
                .type(Type.QR)
                .build();
        TransactionEntity createdTransaction = transactionRepository.save(transactionEntity);

        return new PayResDto().builder()
                .transactionId(createdTransaction.getTransactionId())
                .build();
    }

    @Transactional
    public AccountEntity calcWithdraw(Long withdrawId, int payment, String type){
        AccountEntity withdrawAccount = accountRepository.findById(withdrawId).orElseThrow(() -> new AccountNotFoundException());
        if(type.equals("qr")){
            withdrawAccount.setBalance(withdrawAccount.getBalance() - payment);
        } else if(type.equals("reserve")) {

        } else if(type.equals("refund")) {

        }

        return withdrawAccount;
    }

    @Transactional
    public AccountEntity calcDeposit(Long depositId, int payment, String type){
        AccountEntity depositAccount = accountRepository.findById(depositId).orElseThrow(() -> new AccountNotFoundException());

        if(type.equals("qr")){
            depositAccount.setBalance(depositAccount.getBalance() + payment);
        }

        return depositAccount;
    }

    @Transactional
    public void calcRevenue(Long lessonId, int payment){
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);

        RevenueEntity revenueEntity = revenueRepository.findByCreatedDateBetween(startOfMonth, endOfMonth).orElse(null);

        if (revenueEntity != null) {
            revenueEntity.setRevenue(revenueEntity.getRevenue() + payment);
        } else {
            RevenueEntity newRevenue = RevenueEntity.builder()
                    .lessonEntity(lessonRepository.findById(lessonId).get())
                    .revenue((long) payment)
                    .materialPrice(0)
                    .rentalPrice(0)
                    .etcPrice(0)
                    .build();
            revenueRepository.save(newRevenue);
        }
    }
}
