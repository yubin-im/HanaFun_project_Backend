package com.hanaro.hanafun.lessondate.service.impl;

import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.lessondate.dto.response.LessonDetailResDto;
import com.hanaro.hanafun.lessondate.service.LessonDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
