package com.hanaro.hanafun;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.category.domain.CategoryEntity;
import com.hanaro.hanafun.category.domain.CategoryRepository;
import com.hanaro.hanafun.category.exception.CategoryNotFoundException;
import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.host.domain.HostRepository;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.lesson.dto.request.CreateLessonDateReqDto;
import com.hanaro.hanafun.lesson.dto.request.CreateLessonReqDto;
import com.hanaro.hanafun.lesson.dto.response.LessonInfoResDto;
import com.hanaro.hanafun.lesson.dto.response.OpenedLessonsResDto;
import com.hanaro.hanafun.lesson.exception.LessonNotFoundException;
import com.hanaro.hanafun.lesson.service.impl.LessonServiceImpl;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.user.domain.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LessonServiceTests {
    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private HostRepository hostRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private LessonDateRepository lessonDateRepository;

    @InjectMocks
    LessonServiceImpl lessonService;

    private UserEntity user1;
    private AccountEntity account1;
    private HostEntity host1;
    private CategoryEntity category1;
    private LessonEntity lesson1;
    private LessonEntity lesson2;

    private Long userId1 = 1L;
    private Long lessonId1 = 1L;
    private Long lessonId2 = 2L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = UserEntity.builder()
                .userId(userId1)
                .username("사용자1")
                .password("111111")
                .email("hanaro1@gmail.com")
                .build();

        account1 = AccountEntity.builder()
                .accountId(1L)
                .userEntity(user1)
                .accountNumber("1111-1111-1111")
                .accountName("보통통장1")
                .password("1111")
                .build();

        host1 = HostEntity.builder()
                .hostId(1L)
                .userEntity(user1)
                .accountEntity(account1)
                .build();

        category1 = CategoryEntity.builder()
                .categoryId(1L)
                .categoryName("베이킹")
                .build();

        lesson1 = LessonEntity.builder()
                .lessonId(lessonId1)
                .hostEntity(host1)
                .categoryEntity(category1)
                .title("레슨1")
                .location("장소1")
                .price(50000)
                .capacity(15)
                .image("imageURL1")
                .description("설명1")
                .build();

        lesson2 = LessonEntity.builder()
                .lessonId(lessonId2)
                .hostEntity(host1)
                .categoryEntity(category1)
                .title("레슨2")
                .location("장소2")
                .price(30000)
                .capacity(20)
                .image("imageURL2")
                .description("설명2")
                .build();
    }

    @Test
    @DisplayName("개설 클래스 관리- 개설 클래스 목록 출력 테스트")
    void testOpenedLessons() {
        // Given
        when(hostRepository.findHostEntityByUserEntity_UserId(userId1)).thenReturn(host1);
        when(lessonRepository.findLessonEntitiesByHostEntity(host1)).thenReturn(Arrays.asList(lesson1, lesson2));

        // When
        List<OpenedLessonsResDto> result = lessonService.openedLessons(userId1);

        // Then
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getLessonId());
        assertEquals("imageURL1", result.get(0).getImage());
        assertEquals("레슨1", result.get(0).getTitle());
        assertEquals(2L, result.get(1).getLessonId());
        assertEquals("imageURL2", result.get(1).getImage());
        assertEquals("레슨2", result.get(1).getTitle());
    }

    @Test
    @DisplayName("클래스 상세보기 테스트- 성공시")
    void lessonInfoTest_Success() {
        // Given
        when(lessonRepository.findById(lessonId1)).thenReturn(Optional.of(lesson1));

        // When
        LessonInfoResDto result = lessonService.lessonInfo(lessonId1);

        // Then
        assertEquals(lessonId1, result.getLessonId());
        assertEquals(category1.getCategoryName(), result.getCategoryName());
        assertEquals("레슨1", result.getTitle());
        assertEquals("장소1", result.getLocation());
        assertEquals(50000, result.getPrice());
        assertEquals(15, result.getCapacity());
        assertEquals("imageURL1", result.getImage());
        assertEquals("설명1", result.getDescription());
    }

    @Test
    @DisplayName("클래스 상세보기 테스트- 실패시(LessonNotFoundException)")
    void lessonInfoTest_Fail() {
        // Given
        when(lessonRepository.findById(lessonId1)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(LessonNotFoundException.class, () -> {
            lessonService.lessonInfo(lessonId1);
        });
    }

    @Test
    @DisplayName("클래스 등록하기 테스트- 성공시")
    void createLessonTest() {
        // Given
        CreateLessonDateReqDto lessonDateReqDto1 = CreateLessonDateReqDto.builder()
                .date(LocalDate.of(2024, 7, 1))
                .startTime(LocalDateTime.of(2024, 07,1, 10,0,0))
                .endTime(LocalDateTime.of(2024, 07,1, 12,0,0))
                .build();

        CreateLessonDateReqDto lessonDateReqDto2 = CreateLessonDateReqDto.builder()
                .date(LocalDate.of(2024, 7, 2))
                .startTime(LocalDateTime.of(2024, 7, 2, 14, 0, 0))
                .endTime(LocalDateTime.of(2024, 7, 2, 16, 0, 0))
                .build();

        List<CreateLessonDateReqDto> lessonDateList = Arrays.asList(lessonDateReqDto1, lessonDateReqDto2);

        CreateLessonReqDto createLessonReqDto = CreateLessonReqDto.builder()
                .categoryId(1L)
                .title("레슨1")
                .location("장소1")
                .price(50000)
                .capacity(15)
                .image("imageURL1")
                .description("설명1")
                .lessonDate(lessonDateList)
                .build();

        when(hostRepository.findHostEntityByUserEntity_UserId(userId1)).thenReturn(host1);
        when(categoryRepository.findById(createLessonReqDto.getCategoryId())).thenReturn(Optional.of(category1));

        // When
        lessonService.createLesson(userId1, createLessonReqDto);

        // Then
        verify(lessonRepository, times(1)).save(any(LessonEntity.class));
        verify(lessonDateRepository, times(2)).save(any(LessonDateEntity.class));
    }

    @Test
    @DisplayName("클래스 등록하기 테스트- 실패시(CategoryNotFoundException)")
    void testCreateLesson_CategoryNotFoundException() {
        // Given
        CreateLessonDateReqDto lessonDateReqDto1 = CreateLessonDateReqDto.builder()
                .date(LocalDate.of(2024, 7, 1))
                .startTime(LocalDateTime.of(2024, 07,1, 10,0,0))
                .endTime(LocalDateTime.of(2024, 07,1, 12,0,0))
                .build();

        CreateLessonDateReqDto lessonDateReqDto2 = CreateLessonDateReqDto.builder()
                .date(LocalDate.of(2024, 7, 2))
                .startTime(LocalDateTime.of(2024, 7, 2, 14, 0, 0))
                .endTime(LocalDateTime.of(2024, 7, 2, 16, 0, 0))
                .build();

        List<CreateLessonDateReqDto> lessonDateList = Arrays.asList(lessonDateReqDto1, lessonDateReqDto2);

        CreateLessonReqDto createLessonReqDto = CreateLessonReqDto.builder()
                .categoryId(1L)
                .title("레슨1")
                .location("장소1")
                .price(50000)
                .capacity(15)
                .image("imageURL1")
                .description("설명1")
                .lessonDate(lessonDateList)
                .build();

        when(hostRepository.findHostEntityByUserEntity_UserId(userId1)).thenReturn(host1);
        when(categoryRepository.findById(createLessonReqDto.getCategoryId())).thenReturn(Optional.empty());

        // When, Then
        assertThrows(CategoryNotFoundException.class, () -> {
            lessonService.createLesson(userId1, createLessonReqDto);
        });

        verify(lessonRepository, times(0)).save(any(LessonEntity.class));
        verify(lessonDateRepository, times(0)).save(any(LessonDateEntity.class));
    }
}