package com.hanaro.hanafun.lesson.service.impl;

import com.hanaro.hanafun.category.domain.CategoryEntity;
import com.hanaro.hanafun.category.domain.CategoryRepository;
import com.hanaro.hanafun.category.exception.CategoryNotFoundException;
import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.host.domain.HostRepository;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.lesson.dto.request.CreateLessonDateReqDto;
import com.hanaro.hanafun.lesson.dto.request.CreateLessonReqDto;
import com.hanaro.hanafun.lesson.dto.request.OpenedLessonsReqDto;
import com.hanaro.hanafun.lesson.dto.response.LessonInfoResDto;
import com.hanaro.hanafun.lesson.dto.response.OpenedLessonsResDto;
import com.hanaro.hanafun.lesson.exception.LessonNotFoundException;
import com.hanaro.hanafun.lesson.service.LessonService;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
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
    private final CategoryRepository categoryRepository;
    private final LessonDateRepository lessonDateRepository;

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

    // 클래스 상세보기
    @Transactional
    @Override
    public LessonInfoResDto lessonInfo(Long lessonId) {
        LessonEntity lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new LessonNotFoundException());

        LessonInfoResDto lessonInfoResDto = LessonInfoResDto.builder()
                .lessonId(lessonId)
                .image(lesson.getImage())
                .title(lesson.getTitle())
                .price(lesson.getPrice())
                .description(lesson.getDescription())
                .location(lesson.getLocation())
                .materials(lesson.getMaterials())
                .capacity(lesson.getCapacity())
                .categoryName(lesson.getCategoryEntity().getCategoryName())
                .build();

        return lessonInfoResDto;
    }

    // 클래스 등록하기
    @Transactional
    @Override
    public void createLesson(CreateLessonReqDto createLessonReqDto) {
        HostEntity host = hostRepository.findHostEntityByUserEntity_UserId(createLessonReqDto.getUserId());
        CategoryEntity category = categoryRepository.findById(createLessonReqDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException());

        // Lesson 추가
        LessonEntity lesson = LessonEntity.builder()
                .hostEntity(host)
                .categoryEntity(category)
                .title(createLessonReqDto.getTitle())
                .location(createLessonReqDto.getLocation())
                .price(createLessonReqDto.getPrice())
                .capacity(createLessonReqDto.getCapacity())
                .image(createLessonReqDto.getImage())
                .description(createLessonReqDto.getDescription())
                .materials(createLessonReqDto.getMaterials())
                .build();
        lessonRepository.save(lesson);

        // LessonDate 추가
        List<CreateLessonDateReqDto> lessonDates = createLessonReqDto.getLessonDate();
        for (CreateLessonDateReqDto lessonDateReqDto: lessonDates) {
            LessonDateEntity lessonDate = LessonDateEntity.builder()
                    .lessonEntity(lesson)
                    .date(lessonDateReqDto.getDate())
                    .startTime(lessonDateReqDto.getStartTime())
                    .endTime(lessonDateReqDto.getEndTime())
                    .build();
            lessonDateRepository.save(lessonDate);
        }
    }
}
