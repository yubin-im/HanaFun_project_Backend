package com.hanaro.hanafun.transaction.service.impl;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import com.hanaro.hanafun.account.exception.AccountBalanceException;
import com.hanaro.hanafun.account.exception.AccountNotFoundException;
import com.hanaro.hanafun.hanastorage.domain.HanastorageEntity;
import com.hanaro.hanafun.hanastorage.domain.HanastorageRepository;
import com.hanaro.hanafun.hanastorage.exception.HanastorageNotFoundException;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.lesson.exception.LessonNotFoundException;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.lessondate.exception.LessonDateNotFoundException;
import com.hanaro.hanafun.reservation.domain.ReservationEntity;
import com.hanaro.hanafun.reservation.domain.ReservationRepository;
import com.hanaro.hanafun.reservation.exception.ReservationNotFounException;
import com.hanaro.hanafun.revenue.domain.RevenueEntity;
import com.hanaro.hanafun.revenue.domain.RevenueRepository;
import com.hanaro.hanafun.transaction.domain.TransactionEntity;
import com.hanaro.hanafun.transaction.domain.TransactionRepository;
import com.hanaro.hanafun.transaction.dto.PayResDto;
import com.hanaro.hanafun.transaction.dto.PaybackReqDto;
import com.hanaro.hanafun.transaction.dto.QrReqDto;
import com.hanaro.hanafun.transaction.dto.SimpleReqDto;
import com.hanaro.hanafun.transaction.enums.Type;
import com.hanaro.hanafun.transaction.exception.TransactionNotFoundException;
import com.hanaro.hanafun.transaction.service.TransactionService;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

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

    static String PLUS = "PLUS";
    static String MINUS = "MINUS";

    @Override
    @Transactional //RuntimeException 자동 롤백
    public PayResDto qrPay(QrReqDto qrReqDto) {
        //계좌 가져오기
        AccountEntity withdrawAccount = accountRepository.findById(qrReqDto.getWithdrawId()).orElseThrow(() -> new AccountNotFoundException());
        AccountEntity depositAccount = accountRepository.findById(qrReqDto.getDepositId()).orElseThrow(() -> new AccountNotFoundException());

        //거래 내역 저장 _ QR
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .depositAccount(depositAccount)
                .withdrawAccount(withdrawAccount)
                .reservationEntity(null)
                .payment(qrReqDto.getPayment())
                .point(0)
                .type(Type.QR)
                .build();
        TransactionEntity createdTransaction = transactionRepository.save(transactionEntity);

        //계좌 잔액 업데이트
        calcAccount(withdrawAccount, qrReqDto.getPayment(), MINUS);
        calcAccount(depositAccount, qrReqDto.getPayment(), PLUS);

        //매출 업데이트
        calcRevenue(qrReqDto.getLessonId(), qrReqDto.getPayment());

        return new PayResDto().builder()
                .transactionId(createdTransaction.getTransactionId())
                .build();
    }

    @Override
    @Transactional(noRollbackFor = {AccountBalanceException.class})
    public PayResDto simplePay(Long userId, SimpleReqDto simpleReqDto) {
        //호스트 계좌번호 가져오기
        LessonDateEntity lessonDateEntity = lessonDateRepository.findById(simpleReqDto.getLessondateId())
                .orElseThrow(() -> new LessonDateNotFoundException());
        Long depositId = lessonDateEntity.getLessonEntity().getHostEntity().getAccountEntity().getAccountId();

        //계좌 가져오기
        AccountEntity withdrawAccount = accountRepository.findById(simpleReqDto.getWithdrawId())
                .orElseThrow(() -> new AccountNotFoundException());
        AccountEntity depositAccount = accountRepository.findById(depositId)
                .orElseThrow(() -> new AccountNotFoundException());

        //예약 가져오기
        ReservationEntity reservationEntity = reservationRepository.findById(simpleReqDto.getReservationId())
                .orElseThrow(() -> new ReservationNotFounException());

        //잔액 없으면 예약 취소
        if(withdrawAccount.getBalance() < simpleReqDto.getPayment() - simpleReqDto.getPoint()) {
            cancelReservation(reservationEntity);
            throw new AccountBalanceException();
        }

        //거래 내역 저장 _ PENDING
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .depositAccount(depositAccount)
                .withdrawAccount(withdrawAccount)
                .reservationEntity(reservationEntity)
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

        //계좌 잔액 업데이트
        calcAccount(withdrawAccount, simpleReqDto.getPayment() - simpleReqDto.getPoint(), MINUS);

        //게스트 포인트 소멸
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        userEntity.setPoint(userEntity.getPoint() - simpleReqDto.getPoint());

        return new PayResDto().builder()
                .transactionId(createdTransaction.getTransactionId())
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelReservation(ReservationEntity reservationEntity){
        reservationEntity.updateIsDeleted(true);
        reservationRepository.save(reservationEntity);
    }

    @Override
    @Transactional
    public PayResDto payback(Long userId, PaybackReqDto paybackReqDto) {
        //거래에서 거래 타입 변경 _ CANCELED
        TransactionEntity transactionEntity = transactionRepository
                .findByReservationEntityReservationIdAndType(paybackReqDto.getReservationId(), Type.PENDING)
                .orElseThrow(() -> new TransactionNotFoundException());
        transactionEntity.setType(Type.CANCELED);

        //하나은행 저장소 삭제 처리
        HanastorageEntity hanastorageEntity = hanastorageRepository.findByTransactionEntity(transactionEntity)
                .orElseThrow(() -> new HanastorageNotFoundException());
        hanastorageEntity.setIsDeleted(true);

        //게스트 계좌 잔액 업데이트
        calcAccount(transactionEntity.getWithdrawAccount(), transactionEntity.getPayment() - transactionEntity.getPoint(), PLUS);

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
        List<HanastorageEntity> hanastorageEntityList = hanastorageRepository.findByLessondateAndIsDeleted(yesterday, false)
                .orElseThrow(() -> new HanastorageNotFoundException());
        if(hanastorageEntityList.isEmpty()){
            throw new HanastorageNotFoundException();
        }

        hanastorageEntityList.forEach(this::doAutoTransfer);
    }

    @Transactional
    private void doAutoTransfer(HanastorageEntity hanastorageEntity){
        //하나은행 저장소 삭제 처리
        hanastorageEntity.setIsDeleted(true);

        //거래에서 거래 타입 변경 _ COMPLETED
        TransactionEntity transactionEntity = transactionRepository.findById(hanastorageEntity.getTransactionEntity().getTransactionId())
                        .orElseThrow(() -> new TransactionNotFoundException());
        transactionEntity.setType(Type.COMPLETED);

        //호스트 계좌 잔액 업데이트
        calcAccount(transactionEntity.getDepositAccount(), transactionEntity.getPayment(), PLUS);

        //매출 업데이트
        Long lessonId = transactionEntity
                .getReservationEntity()
                .getLessonDateEntity()
                .getLessonEntity()
                .getLessonId();
        calcRevenue(lessonId, transactionEntity.getPayment());
    }

    @Transactional
    private void calcAccount(AccountEntity accountEntity, int payment, String type){
        if(type.equals(PLUS)){
            accountEntity.setBalance(accountEntity.getBalance() + payment);
        } else if(type.equals(MINUS)) {
            if(accountEntity.getBalance() < payment) throw new AccountBalanceException();
            accountEntity.setBalance(accountEntity.getBalance() - payment);
        }
    }

    @Transactional
    private void calcRevenue(Long lessonId, int payment){
        LessonEntity lessonEntity = lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException());

        //달의 시작과 끝을 설정.
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);

        //없으면 그냥 이후의 로직을 처리함. 예외 처리 X.
        RevenueEntity revenueEntity = revenueRepository.findByLessonEntityAndCreatedDateBetween(lessonEntity, startOfMonth, endOfMonth)
                .orElse(null);

        if (revenueEntity != null) {
            revenueEntity.setRevenue(revenueEntity.getRevenue() + payment);
        } else {
            RevenueEntity newRevenue = RevenueEntity.builder()
                    .lessonEntity(lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException()))
                    .revenue((long) payment)
                    .materialPrice(0)
                    .rentalPrice(0)
                    .etcPrice(0)
                    .build();
            RevenueEntity testR = revenueRepository.save(newRevenue);
            System.out.println("isCOME? " + testR.getRevenue());
        }
    }
}