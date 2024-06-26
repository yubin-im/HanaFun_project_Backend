package com.hanaro.hanafun.reservation.service.impl;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.lessondate.exception.LessonDateNotFoundException;
import com.hanaro.hanafun.reservation.domain.ReservationEntity;
import com.hanaro.hanafun.reservation.domain.ReservationRepository;
import com.hanaro.hanafun.reservation.dto.request.LessonDateDetailReqDto;
import com.hanaro.hanafun.reservation.dto.request.MyPageReqDto;
import com.hanaro.hanafun.reservation.dto.request.MyScheduleReqDto;
import com.hanaro.hanafun.reservation.dto.response.*;
import com.hanaro.hanafun.reservation.service.ReservationService;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
import com.hanaro.hanafun.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final LessonDateRepository lessonDateRepository;

    // 마이페이지 데이터 출력
    @Transactional
    @Override
    public ApiResponse<MyPageResDto> myPage(MyPageReqDto myPageReqDto) {
        UserEntity user = userRepository.findById(myPageReqDto.getUserId()).orElseThrow(() -> new UserNotFoundException());
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
                            .build();
                    return lesson;
                })
                .collect(Collectors.toList());

        MyPageResDto myPageResDto = MyPageResDto.builder()
                .point(user.getPoint())
                .lessons(lessons)
                .build();

        ApiResponse<MyPageResDto> response = new ApiResponse<>(
                true,
                "마이페이지 출력 완료",
                myPageResDto
        );

        return response;
    }

    // 나의 신청 클래스 데이터 출력
    @Transactional
    @Override
    public ApiResponse<List<ReservationList>> myLessons(MyPageReqDto myPageReqDto) {
        UserEntity user = userRepository.findById(myPageReqDto.getUserId()).orElseThrow(() -> new UserNotFoundException());
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
                            .build();
                    return lesson;
                })
                .collect(Collectors.toList());

        ApiResponse<List<ReservationList>> response = new ApiResponse<>(
                true,
                "나의 신청 클래스 출력 완료",
                lessons
        );

        return response;
    }

    // 신청 클래스 일정 데이터 출력
    @Transactional
    @Override
    public ApiResponse<List<MyScheduleResDto>> mySchedules(MyScheduleReqDto myScheduleReqDto) {
        UserEntity user = userRepository.findById(myScheduleReqDto.getUserId()).orElseThrow(() -> new UserNotFoundException());
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

        ApiResponse<List<MyScheduleResDto>> response = new ApiResponse<>(
                true,
                "나의 신청 클래스 출력 완료",
                mySchedules
        );

        return response;
    }

    // 개설 클래스 상세- 강좌날짜 별 예약자 정보 출력
    @Transactional
    @Override
    public ApiResponse<LessonDateDetailResDto> lessonDateDetail(LessonDateDetailReqDto lessonDateDetailReqDto) {
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

        ApiResponse<LessonDateDetailResDto> response = new ApiResponse<>(
                true,
                "예약자 정보 출력 완료",
                lessonDateDetailResDto
        );

        return response;
    }
}
