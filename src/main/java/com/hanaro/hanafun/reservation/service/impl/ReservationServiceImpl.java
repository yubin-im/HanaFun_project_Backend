package com.hanaro.hanafun.reservation.service.impl;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.reservation.domain.ReservationEntity;
import com.hanaro.hanafun.reservation.domain.ReservationRepository;
import com.hanaro.hanafun.reservation.dto.request.MyPageReqDto;
import com.hanaro.hanafun.reservation.dto.response.MyPageResDto;
import com.hanaro.hanafun.reservation.dto.response.ReservationList;
import com.hanaro.hanafun.reservation.service.ReservationService;
import com.hanaro.hanafun.user.domain.UserEntity;
import com.hanaro.hanafun.user.domain.UserRepository;
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

    // 마이페이지 데이터 출력
    @Transactional
    @Override
    public ApiResponse<MyPageResDto> myPage(MyPageReqDto myPageReqDto) {
        UserEntity user = userRepository.findById(myPageReqDto.getUserId()).orElse(null);
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
                .lessons(lessons).build();

        ApiResponse<MyPageResDto> response = new ApiResponse<>(
                true,
                "마이페이지 출력 완료",
                myPageResDto
        );

        return response;
    }
}
