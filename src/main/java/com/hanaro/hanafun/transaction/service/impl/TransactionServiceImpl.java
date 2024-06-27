package com.hanaro.hanafun.transaction.service.impl;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import com.hanaro.hanafun.account.exception.AccountBalanceException;
import com.hanaro.hanafun.account.exception.AccountNotFoundException;
import com.hanaro.hanafun.hanastorage.domain.HanastorageEntity;
import com.hanaro.hanafun.hanastorage.domain.HanastorageRepository;
import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.reservation.domain.ReservationRepository;
import com.hanaro.hanafun.revenue.domain.RevenueEntity;
import com.hanaro.hanafun.revenue.domain.RevenueRepository;
import com.hanaro.hanafun.transaction.domain.TransactionEntity;
import com.hanaro.hanafun.transaction.domain.TransactionRepository;
import com.hanaro.hanafun.transaction.dto.PayResDto;
import com.hanaro.hanafun.transaction.dto.PaybackReqDto;
import com.hanaro.hanafun.transaction.dto.QrReqDto;
import com.hanaro.hanafun.transaction.dto.SimpleReqDto;
import com.hanaro.hanafun.transaction.enums.Type;
import com.hanaro.hanafun.transaction.service.TransactionService;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final LessonDateRepository lessonDateRepository;
    private final ReservationRepository reservationRepository;
    private final RevenueRepository revenueRepository;
    private final AccountRepository accountRepository;
    private final HanastorageRepository hanastorageRepository;
    private final TransactionRepository transactionRepository;

    static private String PLUS = "PLUS";
    static private String MINUS = "MINUS";
    static private String ZERO = "ZERO";

    @Override
    @Transactional
    public PayResDto qrPay(QrReqDto qrReqDto) {
        //계좌 업데이트
        AccountEntity withdrawAccount = calcAccount(qrReqDto.getWithdrawId(), qrReqDto.getPayment(), MINUS);
        AccountEntity depositAccount = calcAccount(qrReqDto.getDepositId(), qrReqDto.getPayment(), PLUS);

        //매출 업데이트
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

    @Override
    @Transactional
    public PayResDto simplePay(Long userId, SimpleReqDto simpleReqDto) {
        //호스트 계좌번호 가져오기
        LessonDateEntity lessonDateEntity = lessonDateRepository.findById(simpleReqDto.getLessondateId())
                .orElse(null);
        Long depositId = lessonDateEntity.getLessonEntity().getHostEntity().getAccountEntity().getAccountId();

        //계좌 잔액 업데이트
        AccountEntity withdrawAccount = calcAccount(simpleReqDto.getWithdrawId(), simpleReqDto.getPayment() - simpleReqDto.getPoint(), MINUS);
        AccountEntity depositAccount = calcAccount(depositId, 0, ZERO);

        //게스트 포인트 소멸
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        userEntity.setPoint(userEntity.getPoint() - simpleReqDto.getPoint());

        //거래 내역 저장 _ PENDING
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .depositAccount(depositAccount)
                .withdrawAccount(withdrawAccount)
                .reservationEntity(reservationRepository.findById(simpleReqDto.getReservationId()).get())
                .payment(simpleReqDto.getPayment())
                .point(simpleReqDto.getPoint())
                .type(Type.PENDING)
                .build();
        TransactionEntity createdTransaction = transactionRepository.save(transactionEntity);

        //하나은행 저장소 저장
        HanastorageEntity hanastorageEntity = HanastorageEntity.builder()
                .transactionEntity(createdTransaction)
                .lessondate(lessonDateEntity.getDate())
                .isDeleted(false)
                .build();
        hanastorageRepository.save(hanastorageEntity);

        return new PayResDto().builder()
                .transactionId(createdTransaction.getTransactionId())
                .build();
    }

    @Override
    @Transactional
    public PayResDto payback(Long userId, PaybackReqDto paybackReqDto) {
        //거래에서 거래 타입 변경
        TransactionEntity transactionEntity = transactionRepository.findByReservationEntityReservationIdAndType(paybackReqDto.getReservationId(), Type.PENDING).orElseThrow();
        transactionEntity.setType(Type.CANCELED);

        //하나은행 저장소 삭제 처리
        HanastorageEntity hanastorageEntity = hanastorageRepository.findByTransactionEntityTransactionId(transactionEntity.getTransactionId()).orElseThrow();
        hanastorageEntity.setIsDeleted(true);

        //게스트 계좌에 송금
        Long guestAccountId = transactionEntity.getWithdrawAccount().getAccountId();
        calcAccount(guestAccountId, transactionEntity.getPayment() - transactionEntity.getPoint(), PLUS);

        //게스트 포인트 적립
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        userEntity.setPoint(userEntity.getPoint() + transactionEntity.getPoint());

        return new PayResDto().builder()
                .transactionId(transactionEntity.getTransactionId())
                .build();
    }

    @Transactional
    public void autoTransfer(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<HanastorageEntity> hanastorageEntityList = hanastorageRepository.findByLessondateAndIsDeleted(yesterday, false);
        hanastorageEntityList.forEach(this::doAutoTransfer);
    }

    @Transactional
    private void doAutoTransfer(HanastorageEntity hanastorageEntity){
        //하나은행 저장소 삭제 처리
        hanastorageEntity.setIsDeleted(true);

        //거래에서 거래 타입 변경
        TransactionEntity transactionEntity = transactionRepository.findById(hanastorageEntity.getTransactionEntity().getTransactionId())
                        .orElse(null);
        transactionEntity.setType(Type.COMPLETED);

        //호스트 계좌에 송금
        Long hostAccountId = transactionEntity.getDepositAccount().getAccountId();
        calcAccount(hostAccountId, transactionEntity.getPayment(), PLUS);

        //매출 업데이트
        Long lessonId = transactionEntity
                .getReservationEntity()
                .getLessonDateEntity()
                .getLessonEntity()
                .getLessonId();
        calcRevenue(lessonId, transactionEntity.getPayment());
    }

    @Transactional
    private AccountEntity calcAccount(Long accountId, int payment, String type){
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException());

        if(type.equals(PLUS)){
            accountEntity.setBalance(accountEntity.getBalance() + payment);
        } else if(type.equals(MINUS)) {
            if(accountEntity.getBalance() < payment){
                throw new AccountBalanceException();
            }
            accountEntity.setBalance(accountEntity.getBalance() - payment);
        }

        return accountEntity;
    }

    @Transactional
    private void calcRevenue(Long lessonId, int payment){
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);

        RevenueEntity revenueEntity = revenueRepository.findByCreatedDateBetween(startOfMonth, endOfMonth)
                .orElse(null);

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