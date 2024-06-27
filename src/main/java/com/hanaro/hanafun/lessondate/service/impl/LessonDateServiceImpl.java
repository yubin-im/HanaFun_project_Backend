package com.hanaro.hanafun.lessondate.service.impl;

import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.lessondate.dto.request.AvailableDateReqDto;
import com.hanaro.hanafun.lessondate.dto.response.AvailableDateResDto;
import com.hanaro.hanafun.lessondate.dto.response.LessonDetailResDto;
import com.hanaro.hanafun.lessondate.service.LessonDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class LessonDateServiceImpl implements LessonDateService {
    private final LessonDateRepository lessonDateRepository;

    // 개설 클래스 상세
    @Transactional
    @Override
    public List<LessonDetailResDto> lessonDetail(Long lessonId) {
        List<LessonDateEntity> lessonDates = lessonDateRepository.findLessonDateEntitiesByLessonEntity_LessonId(lessonId);

        List<LessonDetailResDto> lessonDetails = lessonDates.stream()
                .map(lessonDate -> {
                    LessonDetailResDto lessonDetail = LessonDetailResDto.builder()
                            .lessondateId(lessonDate.getLessondateId())
                            .date(lessonDate.getDate())
                            .lessonId(lessonId)
                            .build();
                    return lessonDetail;
                })
                .collect(Collectors.toList());

        return lessonDetails;
    }

    // 클래스 예약 가능 날짜 출력
    @Transactional
    @Override
    public List<AvailableDateResDto> availableDate(AvailableDateReqDto availableDateReqDto) {
        List<LessonDateEntity> lessonDates = lessonDateRepository.findLessonDateEntitiesByLessonEntity_LessonId(availableDateReqDto.getLessonId());

        List<AvailableDateResDto> availableDateResDtos = lessonDates.stream()
                .map(lessonDate -> {
                    LessonEntity lesson = lessonDate.getLessonEntity();
                    int quantityLeft = lesson.getCapacity() - lessonDate.getApplicant();

                    // 잔여 수량 0 이하 날짜 필터링
                    if (quantityLeft <= 0) {
                        return null;
                    }

                    return AvailableDateResDto.builder()
                            .lessondateId(lessonDate.getLessondateId())
                            .date(lessonDate.getDate())
                            .startTime(lessonDate.getStartTime())
                            .endTime(lessonDate.getEndTime())
                            .quantityLeft(quantityLeft)
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return availableDateResDtos;
    }
}
