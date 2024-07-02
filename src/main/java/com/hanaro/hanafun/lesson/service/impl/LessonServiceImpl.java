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
import com.hanaro.hanafun.lesson.dto.response.FullLessonResDto;
import com.hanaro.hanafun.lesson.dto.response.LessonInfoResDto;
import com.hanaro.hanafun.lesson.dto.response.OpenedLessonsResDto;
import com.hanaro.hanafun.lesson.exception.LessonNotFoundException;
import com.hanaro.hanafun.lesson.service.LessonService;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public List<OpenedLessonsResDto> openedLessons(Long userId) {
        HostEntity host = hostRepository.findHostEntityByUserEntity_UserId(userId);
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

    // 클래스 전체 조회 (클래스 탐색)
    @Transactional
    @Override
    public List<FullLessonResDto> fullLesson() {
        List<LessonEntity> lessonList = lessonRepository.findAll();

        return searchLessonList(lessonList);
    }

    // 카테고리 별 클래스 조회
    @Transactional
    @Override
    public List<FullLessonResDto> categoryLesson(Long categoryId) {
        List<LessonEntity> lessonList = lessonRepository.findByCategoryEntityCategoryId(categoryId)
                .orElseThrow(()->new CategoryNotFoundException());

        if(lessonList.isEmpty()) throw new CategoryNotFoundException();

        return searchLessonList(lessonList);
    }

    // 클래스 검색(전체)
    @Transactional
    @Override
    public List<FullLessonResDto> searchLessonAll(String query) {
        List<LessonEntity> lessonList = lessonRepository.findBySearchLessonAll(query);

        return searchLessonList(lessonList);
    }

    // 클래스 검색(카테고리)
    @Transactional
    @Override
    public List<FullLessonResDto> searchLessonCategory(Long categoryId, String query) {
        List<LessonEntity> lessonList = lessonRepository.findBySearchLessonCategory(categoryId, query);

        return searchLessonList(lessonList);
    }

    // 클래스 필터(전체)
    @Transactional
    @Override
    public List<FullLessonResDto> searchFilterLessonAll(String query, String sort) {
        List<LessonEntity> lessonList = switch (sort) {
            case "date" -> lessonRepository.findSearchFilterLessonAllByOrderByDate(query);
            case "popular" -> lessonRepository.findSearchFilterLessonAllByOrderByApplicantSum(query);
            case "priceAsc" -> lessonRepository.findSearchFilterLessonAllByOrderByPriceAsc(query);
            case "priceDesc" -> lessonRepository.findSearchFilterLessonAllByOrderByPriceDesc(query);
            default -> throw new LessonNotFoundException();
        };

        return searchLessonList(lessonList);
    }

    @Transactional
    @Override
    public List<FullLessonResDto> searchFilterLessonCategory(Long categoryId, String query, String sort) {
        List<LessonEntity> lessonList = switch (sort) {
            case "date" -> lessonRepository.findSearchFilterLessonCategoryByOrderByDate(categoryId, query);
            case "popular" -> lessonRepository.findSearchFilterLessonCategoryByOrderByApplicantSum(categoryId, query);
            case "priceAsc" -> lessonRepository.findSearchFilterLessonCategoryByOrderByPriceAsc(categoryId, query);
            case "priceDesc" -> lessonRepository.findSearchFilterLessonCategoryByOrderByPriceDesc(categoryId, query);
            default -> throw new LessonNotFoundException();
        };
        return searchLessonList(lessonList);
    }

    public List<FullLessonResDto> searchLessonList(List<LessonEntity> lessonlist){
        return lessonlist.stream().map(lesson -> {
            FullLessonResDto fullLessonResDto = FullLessonResDto.builder()
                    .lessonId(lesson.getLessonId())
                    .image(lesson.getImage())
                    .title(lesson.getTitle())
                    .price(lesson.getPrice())
                    .hostName(lesson.getHostEntity().getUserEntity().getUsername())
                    .build();
            return fullLessonResDto;
        }).collect(Collectors.toList());
    }
}
