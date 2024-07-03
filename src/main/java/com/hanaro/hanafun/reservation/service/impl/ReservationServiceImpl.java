package com.hanaro.hanafun.reservation.service.impl;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.account.domain.AccountRepository;
import com.hanaro.hanafun.account.exception.AccountNotFoundException;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.lessondate.exception.LessonDateNotFoundException;
import com.hanaro.hanafun.reservation.domain.ReservationEntity;
import com.hanaro.hanafun.reservation.domain.ReservationRepository;
import com.hanaro.hanafun.reservation.dto.request.*;
import com.hanaro.hanafun.reservation.dto.response.*;
import com.hanaro.hanafun.reservation.exception.ReservationNotFounException;
import com.hanaro.hanafun.reservation.service.ReservationService;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final LessonDateRepository lessonDateRepository;
    private final AccountRepository accountRepository;
    private final LessonRepository lessonRepository;

    // 마이페이지 데이터 출력
    @Transactional
    @Override
    public MyPageResDto myPage(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        List<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByUserEntity(user);

        LocalDate today = LocalDate.now();  // 오늘이후 날짜의 예약만 출력

        List<ReservationList> lessons = reservations.stream()
                .filter(reservation -> !reservation.getLessonDateEntity().getDate().isBefore(today))
                .map(reservation -> {
                    LessonDateEntity lessonDate = reservation.getLessonDateEntity();
                    LessonEntity lessonEntity = lessonDate.getLessonEntity();

                    ReservationList lesson = ReservationList.builder()
                            .reservationId(reservation.getReservationId())
                            .lessondateId(lessonDate.getLessondateId())
                            .lessonId(lessonEntity.getLessonId())
                            .image(lessonEntity.getImage())
                            .title(lessonEntity.getTitle())
                            .location(lessonEntity.getLocation())
                            .date(lessonDate.getDate())
                            .categoryName(lessonEntity.getCategoryEntity().getCategoryName())
                            .build();
                    return lesson;
                })
                .collect(Collectors.toList());

        MyPageResDto myPageResDto = MyPageResDto.builder()
                .point(user.getPoint())
                .lessons(lessons)
                .build();

        return myPageResDto;
    }

    // 나의 신청 클래스 데이터 출력
    @Transactional
    @Override
    public List<ReservationList> myLessons(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        List<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByUserEntity(user);

        List<ReservationList> lessons = reservations.stream()
                .map(reservation -> {
                    LessonDateEntity lessonDate = reservation.getLessonDateEntity();
                    LessonEntity lessonEntity = lessonDate.getLessonEntity();

                    ReservationList lesson = ReservationList.builder()
                            .reservationId(reservation.getReservationId())
                            .lessondateId(lessonDate.getLessondateId())
                            .lessonId(lessonEntity.getLessonId())
                            .image(lessonEntity.getImage())
                            .title(lessonEntity.getTitle())
                            .location(lessonEntity.getLocation())
                            .date(lessonDate.getDate())
                            .categoryName(lessonEntity.getCategoryEntity().getCategoryName())
                            .build();
                    return lesson;
                })
                .collect(Collectors.toList());

        return lessons;
    }

    // 신청 클래스 일정 데이터 출력
    @Transactional
    @Override
    public List<MyScheduleResDto> mySchedules(Long userId, MyScheduleReqDto myScheduleReqDto) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        List<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByUserEntity(user);

        List<MyScheduleResDto> mySchedules = reservations.stream()
                .filter(reservation -> {
                    LocalDate date = reservation.getLessonDateEntity().getDate();
                    return date.getYear() == myScheduleReqDto.getYear() && date.getMonthValue() == myScheduleReqDto.getMonth();
                })
                .map(reservation -> {
                    LessonDateEntity lessonDate = reservation.getLessonDateEntity();
                    LessonEntity lessonEntity = lessonDate.getLessonEntity();

                    MyScheduleResDto mySchedule = MyScheduleResDto.builder()
                            .reservationId(reservation.getReservationId())
                            .lessonId(lessonEntity.getLessonId())
                            .date(lessonDate.getDate())
                            .title(lessonEntity.getTitle())
                            .build();

                    return mySchedule;
                })
                .collect(Collectors.toList());

        return mySchedules;
    }

    // 개설 클래스 상세- 강좌날짜 별 예약자 정보 출력
    @Transactional
    @Override
    public LessonDateDetailResDto lessonDateDetail(LessonDateDetailReqDto lessonDateDetailReqDto) {
        Long lessondateId = lessonDateDetailReqDto.getLessondateId();
        LessonDateEntity lessonDate = lessonDateRepository.findById(lessondateId).orElseThrow(() -> new LessonDateNotFoundException());
        List<ReservationEntity> reservations = reservationRepository.findReservationEntitiesByLessonDateEntity_LessondateId(lessondateId);

        int applicant = lessonDate.getApplicant();  // 신청인원
        int capacity = lessonDate.getLessonEntity().getCapacity();  // 모집인원

        List<ReservationPerson> people = reservations.stream()
                .map(reservation -> {
                    ReservationPerson person = ReservationPerson.builder()
                            .startTime(lessonDate.getStartTime())
                            .userName(reservation.getUserEntity().getUsername())
                            .email(reservation.getUserEntity().getEmail())
                            .build();
                    return person;
                })
                .collect(Collectors.toList());

        LessonDateDetailResDto lessonDateDetailResDto = LessonDateDetailResDto.builder()
                .applicant(applicant)
                .capacity(capacity)
                .people(people)
                .build();

        return lessonDateDetailResDto;
    }

    // 클래스 예약하기 (결제 제외)
    @Transactional
    @Override
    public BookLessonResDto bookLesson(Long userId, BookLessonReqDto bookLessonReqDto) {
        // 계좌 비밀번호 확인
        AccountEntity account = accountRepository.findById(bookLessonReqDto.getAccountId()).orElseThrow(() -> new AccountNotFoundException());
        if (!account.getPassword().equals(bookLessonReqDto.getPassword())) {
            return BookLessonResDto.builder()
                    .message("계좌 비밀번호가 맞지 않습니다.")
                    .build();
        }

        // 모집인원 초과 확인
        LessonDateEntity lessonDate = lessonDateRepository.findLessonDateEntityByLessondateId(bookLessonReqDto.getLessondateId()).orElseThrow(() -> new LessonDateNotFoundException());
        LessonEntity lesson = lessonDate.getLessonEntity();
        if(lesson.getCapacity() < lessonDate.getApplicant() + bookLessonReqDto.getApplicant()) {
            return BookLessonResDto.builder()
                    .message("모집인원이 초과되었습니다.")
                    .build();
        }

        // 해당 날짜에 예약 이미 있는지 확인
        Optional<ReservationEntity> existingReservation = reservationRepository.findReservationEntityByUserEntity_UserIdAndLessonDateEntity_LessondateId(userId, bookLessonReqDto.getLessondateId());
        if (existingReservation.isPresent()) {
            return BookLessonResDto.builder()
                    .message("이미 예약이 존재합니다.")
                    .build();
        }

        // 예약 추가
        UserEntity user = userRepository.findUserEntityByUserId(userId).orElseThrow(() -> new UserNotFoundException());
        ReservationEntity reservation = ReservationEntity.builder()
                .userEntity(user)
                .lessonDateEntity(lessonDate)
                .applicant(bookLessonReqDto.getApplicant())
                .build();
        reservationRepository.save(reservation);

        // 강좌 신청인원 증가
        lessonDate.updateApplicant(lessonDate.getApplicant() + bookLessonReqDto.getApplicant());
        lessonDateRepository.save(lessonDate);

        // 강좌 신청 누적인원 증가
        lesson.updateApplicantSum(lesson.getApplicantSum() + bookLessonReqDto.getApplicant());
        lessonRepository.save(lesson);

        return BookLessonResDto.builder()
                .message("예약완료")
                .build();
    }

    // 클래스 취소하기 (환불 제외)
    @Transactional
    @Override
    public void cancelLesson(CancelLessonReqDto cancelLessonReqDto) {
        ReservationEntity reservation = reservationRepository.findById(cancelLessonReqDto.getReservationId()).orElseThrow(() -> new ReservationNotFounException());

        // 클래스 취소 (논리적 삭제)
        reservation.updateIsDeleted(true);
        reservationRepository.save(reservation);

        // 신청 인원 제거
        LessonDateEntity lessonDate = reservation.getLessonDateEntity();
        lessonDate.updateApplicant(lessonDate.getApplicant() - reservation.getApplicant());
        lessonDateRepository.save(lessonDate);

        // 강좌 신청 누적인원 제거
        LessonEntity lesson = lessonDate.getLessonEntity();
        lesson.updateApplicantSum(lesson.getApplicantSum() - reservation.getApplicant());
        lessonRepository.save(lesson);
    }
}
