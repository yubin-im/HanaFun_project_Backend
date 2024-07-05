package com.hanaro.hanafun;

import com.hanaro.hanafun.account.domain.*;
import com.hanaro.hanafun.category.domain.CategoryEntity;
import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.lesson.domain.*;
import com.hanaro.hanafun.lessondate.domain.*;
import com.hanaro.hanafun.reservation.domain.*;
import com.hanaro.hanafun.reservation.dto.request.*;
import com.hanaro.hanafun.reservation.dto.response.*;
import com.hanaro.hanafun.reservation.service.impl.ReservationServiceImpl;
import com.hanaro.hanafun.user.domain.*;
import com.hanaro.hanafun.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTests {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LessonDateRepository lessonDateRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    ReservationServiceImpl reservationService;

    private UserEntity user1;
    private AccountEntity account1;
    private HostEntity host1;
    private CategoryEntity category1;
    private LessonEntity lesson1;
    private LessonEntity lesson2;
    private LessonDateEntity lessonDate1;
    private LessonDateEntity lessonDate2;
    private ReservationEntity reservation1;
    private ReservationEntity reservation2;

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
                .point(500)
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
                .applicant(10)
                .date(LocalDate.of(2024, 7, 30))
                .startTime(LocalDateTime.of(2024, 7,30, 10,0,0))
                .endTime(LocalDateTime.of(2024, 7,30, 12,0,0))
                .build();

        lessonDate2 = LessonDateEntity.builder()
                .lessondateId(2L)
                .lessonEntity(lesson2)
                .applicant(10)
                .date(LocalDate.of(2024, 8, 2))
                .startTime(LocalDateTime.of(2024, 8, 2, 14, 0, 0))
                .endTime(LocalDateTime.of(2024, 8, 2, 16, 0, 0))
                .build();

        reservation1 = ReservationEntity.builder()
                .reservationId(1L)
                .userEntity(user1)
                .lessonDateEntity(lessonDate1)
                .applicant(1)
                .build();

        reservation2 = ReservationEntity.builder()
                .reservationId(2L)
                .userEntity(user1)
                .lessonDateEntity(lessonDate2)
                .applicant(1)
                .build();
    }

    @Test
    @DisplayName("마이페이지 데이터 출력 테스트")
    void myPageTest() {
        // Given
        when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        when(reservationRepository.findReservationEntitiesByUserEntity(user1)).thenReturn(Collections.singletonList(reservation1));

        // When
        MyPageResDto result = reservationService.myPage(userId1);

        // Then
        assertNotNull(result);
        assertEquals(user1.getPoint(), result.getPoint());
        assertEquals(1, result.getLessons().size());

        ReservationList lessonResult = result.getLessons().get(0);
        assertEquals(reservation1.getReservationId(), lessonResult.getReservationId());
        assertEquals(lesson1.getLessonId(), lessonResult.getLessonId());
        assertEquals(lesson1.getImage(), lessonResult.getImage());
        assertEquals(lesson1.getTitle(), lessonResult.getTitle());
        assertEquals(lesson1.getLocation(), lessonResult.getLocation());
        assertEquals(reservation1.getLessonDateEntity().getDate(), lessonResult.getDate());
        assertEquals(reservation1.getLessonDateEntity().getLessondateId(), lessonResult.getLessondateId());
        assertEquals(lesson1.getCategoryEntity().getCategoryName(), lessonResult.getCategoryName());
    }

    @Test
    @DisplayName("마이페이지 데이터 출력 테스트- 실패시")
    void myPageTest_UserNotFound() {
        Long userId = 3L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            reservationService.myPage(userId);
        });
    }

    @Test
    @DisplayName("나의 신청 클래스 데이터 출력 테스트")
    void myLessonsTest() {
        // Given
        when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        when(reservationRepository.findReservationEntitiesByUserEntity(user1)).thenReturn(List.of(reservation1, reservation2));

        // When
        List<ReservationList> result = reservationService.myLessons(userId1);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        ReservationList lessonResult1 = result.get(0);
        assertEquals(reservation1.getReservationId(), lessonResult1.getReservationId());
        assertEquals(lesson1.getLessonId(), lessonResult1.getLessonId());
        assertEquals(lesson1.getImage(), lessonResult1.getImage());
        assertEquals(lesson1.getTitle(), lessonResult1.getTitle());
        assertEquals(lesson1.getLocation(), lessonResult1.getLocation());
        assertEquals(reservation1.getLessonDateEntity().getLessondateId(), lessonResult1.getLessondateId());
        assertEquals(reservation1.getLessonDateEntity().getDate(), lessonResult1.getDate());
        assertEquals(lesson1.getCategoryEntity().getCategoryName(), lessonResult1.getCategoryName());

        ReservationList lessonResult2 = result.get(1);
        assertEquals(reservation2.getReservationId(), lessonResult2.getReservationId());
        assertEquals(lesson2.getLessonId(), lessonResult2.getLessonId());
        assertEquals(lesson2.getImage(), lessonResult2.getImage());
        assertEquals(lesson2.getTitle(), lessonResult2.getTitle());
        assertEquals(lesson2.getLocation(), lessonResult2.getLocation());
        assertEquals(reservation2.getLessonDateEntity().getLessondateId(), lessonResult2.getLessondateId());
        assertEquals(reservation2.getLessonDateEntity().getDate(), lessonResult2.getDate());
        assertEquals(lesson2.getCategoryEntity().getCategoryName(), lessonResult2.getCategoryName());
    }

    @Test
    @DisplayName("신청 클래스 일정 데이터 출력 테스트")
    void mySchedulesTest() {
        // Given
        when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        when(reservationRepository.findReservationEntitiesByUserEntity(user1)).thenReturn(List.of(reservation1, reservation2));

        MyScheduleReqDto myScheduleReqDto = MyScheduleReqDto.builder()
                .year(2024)
                .month(7)
                .build();

        // When
        List<MyScheduleResDto> result = reservationService.mySchedules(userId1, myScheduleReqDto);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        MyScheduleResDto scheduleResult = result.get(0);
        assertEquals(reservation1.getReservationId(), scheduleResult.getReservationId());
        assertEquals(lesson1.getLessonId(), scheduleResult.getLessonId());
        assertEquals(reservation1.getLessonDateEntity().getDate(), scheduleResult.getDate());
        assertEquals(lesson1.getTitle(), scheduleResult.getTitle());
    }

    @Test
    @DisplayName("개설 클래스 상세- 강좌날짜 별 예약자 정보 출력 테스트")
    void lessonDateDetailTest() {
        // Given
        LessonDateDetailReqDto lessonDateDetailReqDto = LessonDateDetailReqDto.builder()
                .lessondateId(1L)
                .build();

        when(lessonDateRepository.findById(1L)).thenReturn(Optional.of(lessonDate1));
        when(reservationRepository.findReservationEntitiesByLessonDateEntity_LessondateId(1L)).thenReturn(List.of(reservation1));

        // When
        LessonDateDetailResDto result = reservationService.lessonDateDetail(lessonDateDetailReqDto);

        // Then
        assertNotNull(result);
        assertEquals(lessonDate1.getApplicant(), result.getApplicant());
        assertEquals(lesson1.getCapacity(), result.getCapacity());
        assertEquals(1, result.getPeople().size());

        ReservationPerson personResult = result.getPeople().get(0);
        assertEquals(user1.getUsername(), personResult.getUserName());
        assertEquals(user1.getEmail(), personResult.getEmail());
        assertEquals(lessonDate1.getStartTime(), personResult.getStartTime());
    }

    @Test
    @DisplayName("클래스 예약하기 (결제 제외) 테스트")
    void bookLessonTest() {
        // Given
        BookLessonReqDto bookLessonReqDto = BookLessonReqDto.builder()
                .accountId(1L)
                .password("1111")
                .lessondateId(1L)
                .applicant(1)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        when(lessonDateRepository.findLessonDateEntityByLessondateId(1L)).thenReturn(Optional.of(lessonDate1));
        when(reservationRepository.findReservationEntityByUserEntity_UserIdAndLessonDateEntity_LessondateId(userId1, 1L)).thenReturn(Optional.empty());
        when(userRepository.findUserEntityByUserId(userId1)).thenReturn(Optional.of(user1));

        // When
        BookLessonResDto result = reservationService.bookLesson(userId1, bookLessonReqDto);

        // Then
        assertNotNull(result);

        verify(reservationRepository, times(1)).save(any(ReservationEntity.class));
        verify(lessonDateRepository, times(1)).save(any(LessonDateEntity.class));
        verify(lessonRepository, times(1)).save(any(LessonEntity.class));
    }

    @Test
    @DisplayName("클래스 예약하기 테스트- 비밀번호 틀림")
    void bookLessonTest_WrongPassword() {
        // Given
        BookLessonReqDto bookLessonReqDto = BookLessonReqDto.builder()
                .accountId(1L)
                .password("wrong_password")
                .lessondateId(1L)
                .applicant(1)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));

        // When
        BookLessonResDto result = reservationService.bookLesson(userId1, bookLessonReqDto);

        // Then
        assertNotNull(result);
        assertEquals("계좌 비밀번호가 맞지 않습니다.", result.getMessage());

        verify(reservationRepository, times(0)).save(any(ReservationEntity.class));
        verify(lessonDateRepository, times(0)).save(any(LessonDateEntity.class));
        verify(lessonRepository, times(0)).save(any(LessonEntity.class));
    }

    @Test
    @DisplayName("클래스 예약하기- 모집인원 초과")
    void bookLessonTest_OverCapacity() {
        // Given
        BookLessonReqDto bookLessonReqDto = BookLessonReqDto.builder()
                .accountId(1L)
                .password("1111")
                .lessondateId(1L)
                .applicant(30)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        when(lessonDateRepository.findLessonDateEntityByLessondateId(1L)).thenReturn(Optional.of(lessonDate1));

        // When
        BookLessonResDto result = reservationService.bookLesson(userId1, bookLessonReqDto);

        // Then
        assertNotNull(result);
        assertEquals("모집인원이 초과되었습니다.", result.getMessage());

        verify(reservationRepository, times(0)).save(any(ReservationEntity.class));
        verify(lessonDateRepository, times(0)).save(any(LessonDateEntity.class));
        verify(lessonRepository, times(0)).save(any(LessonEntity.class));
    }

    @Test
    @DisplayName("클래스 예약하기- 예약 존재")
    void bookLessonTest_ExistingReservation() {
        // Given
        BookLessonReqDto bookLessonReqDto = BookLessonReqDto.builder()
                .accountId(1L)
                .password("1111")
                .lessondateId(1L)
                .applicant(1)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        when(lessonDateRepository.findLessonDateEntityByLessondateId(1L)).thenReturn(Optional.of(lessonDate1));
        when(reservationRepository.findReservationEntityByUserEntity_UserIdAndLessonDateEntity_LessondateId(userId1, 1L)).thenReturn(Optional.of(reservation1));

        // When
        BookLessonResDto result = reservationService.bookLesson(userId1, bookLessonReqDto);

        // Then
        assertNotNull(result);
        assertEquals("이미 예약이 존재합니다.", result.getMessage());

        verify(reservationRepository, times(0)).save(any(ReservationEntity.class));
        verify(lessonDateRepository, times(0)).save(any(LessonDateEntity.class));
        verify(lessonRepository, times(0)).save(any(LessonEntity.class));
    }

    @Test
    @DisplayName("클래스 취소하기 (환불 제외) 테스트")
    void cancelLessonTest() {
        // Given
        CancelLessonReqDto cancelLessonReqDto = CancelLessonReqDto.builder()
                .reservationId(1L)
                .build();

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation1));

        // When
        CancelLessonResDto result = reservationService.cancelLesson(cancelLessonReqDto);

        // Then
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("취소 성공", result.getMessage());

        verify(reservationRepository, times(1)).save(reservation1);
        verify(lessonDateRepository, times(1)).save(lessonDate1);
        verify(lessonRepository, times(1)).save(lesson1);
        assertTrue(reservation1.isDeleted());
        assertEquals(9, lessonDate1.getApplicant());
    }

    @Test
    @DisplayName("클래스 취소하기 (환불 제외) 테스트- 예약 없음")
    void cancelLessonTest_NoReservation() {
        // Given
        CancelLessonReqDto cancelLessonReqDto = CancelLessonReqDto.builder()
                .reservationId(99L) // 존재하지 않는 예약 ID
                .build();

        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        CancelLessonResDto result = reservationService.cancelLesson(cancelLessonReqDto);

        // Then
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("취소 실패: 예약을 찾을 수 없습니다.", result.getMessage());

        verify(reservationRepository, times(0)).save(any(ReservationEntity.class));
        verify(lessonDateRepository, times(0)).save(any(LessonDateEntity.class));
        verify(lessonRepository, times(0)).save(any(LessonEntity.class));
    }
}
