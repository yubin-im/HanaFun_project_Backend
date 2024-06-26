package com.hanaro.hanafun.lesson.service.impl;

import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.host.domain.HostRepository;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.lesson.dto.request.OpenedLessonsReqDto;
import com.hanaro.hanafun.lesson.dto.response.OpenedLessonsResDto;
import com.hanaro.hanafun.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final HostRepository hostRepository;

    // 개설 클래스 관리- 개설 클래스 목록 출력
    @Transactional
    @Override
    public List<OpenedLessonsResDto> openedLessons(OpenedLessonsReqDto openedLessonsReqDto) {
        HostEntity host = hostRepository.findHostEntityByUserEntity_UserId(openedLessonsReqDto.getUserId());
        List<LessonEntity> lessons = lessonRepository.findLessonEntitiesByHostEntity(host);

        List<OpenedLessonsResDto> openedLessons = lessons.stream()
                .map(lesson -> {
                    OpenedLessonsResDto openedLesson = OpenedLessonsResDto.builder()
                            .lessonId(lesson.getLessonId())
                            .image(lesson.getImage())
                            .title(lesson.getTitle())
                            .build();
                    return openedLesson;
                })
                .collect(Collectors.toList());

        return openedLessons;
    }
}
