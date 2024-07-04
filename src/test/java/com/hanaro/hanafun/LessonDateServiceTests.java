package com.hanaro.hanafun;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.category.domain.CategoryEntity;
import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateRepository;
import com.hanaro.hanafun.lessondate.dto.request.AvailableDateReqDto;
import com.hanaro.hanafun.lessondate.dto.response.AvailableDateResDto;
import com.hanaro.hanafun.lessondate.dto.response.LessonDetailResDto;
import com.hanaro.hanafun.lessondate.service.impl.LessonDateServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LessonDateServiceTests {
    @Mock
    private LessonDateRepository lessonDateRepository;

    @InjectMocks
    LessonDateServiceImpl lessonDateService;

    private UserEntity user1;
    private AccountEntity account1;
    private HostEntity host1;
    private CategoryEntity category1;
    private LessonEntity lesson1;
    private LessonEntity lesson2;
    private LessonDateEntity lessonDate1;
    private LessonDateEntity lessonDate2;

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

        lessonDate1 = LessonDateEntity.builder()
                .lessondateId(1L)
                .lessonEntity(lesson1)
                .date(LocalDate.of(2024, 7, 1))
                .startTime(LocalDateTime.of(2024, 07,1, 10,0,0))
                .endTime(LocalDateTime.of(2024, 07,1, 12,0,0))
                .build();

        lessonDate2 = LessonDateEntity.builder()
                .lessondateId(2L)
                .lessonEntity(lesson1)
                .date(LocalDate.of(2024, 7, 2))
                .startTime(LocalDateTime.of(2024, 7, 2, 14, 0, 0))
                .endTime(LocalDateTime.of(2024, 7, 2, 16, 0, 0))
                .build();
    }

    @Test
    @DisplayName("개설 클래스 상세 테스트")
    void lessonDetailTest() {
        // Given
        when(lessonDateRepository.findLessonDateEntitiesByLessonEntity_LessonId(lessonId1)).thenReturn(Arrays.asList(lessonDate1, lessonDate2));

        // When
        List<LessonDetailResDto> result = lessonDateService.lessonDetail(lessonId1);

        // Then
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getLessondateId());
        assertEquals(LocalDate.of(2024, 7, 1), result.get(0).getDate());
        assertEquals(lessonId1, result.get(0).getLessonId());
        assertEquals("레슨1", result.get(0).getTitle());

        assertEquals(2L, result.get(1).getLessondateId());
        assertEquals(LocalDate.of(2024, 7, 2), result.get(1).getDate());
        assertEquals(lessonId1, result.get(1).getLessonId());
        assertEquals("레슨1", result.get(1).getTitle());
    }

    @Test
    @DisplayName("클래스 예약 가능 날짜 출력 테스트")
    void availableDateTest() {
        // Given
        LocalDate today = LocalDate.now();

        when(lessonDateRepository.findLessonDateEntitiesByLessonEntity_LessonId(lessonId1)).thenReturn(Arrays.asList(lessonDate1, lessonDate2));

        AvailableDateReqDto availableDateReqDto = AvailableDateReqDto.builder()
                .lessonId(lessonId1)
                .build();

        // When
        List<AvailableDateResDto> result = lessonDateService.availableDate(availableDateReqDto);

        // Then
        assertEquals(0, result.size());
    }
}
