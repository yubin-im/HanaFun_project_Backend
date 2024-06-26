package com.hanaro.hanafun.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LessonDateDetailResDto {
    private int applicant;  // lessondate의 신청인원
    private int capacity;  // lesson의 모집인원
    private List<ReservationPerson> people;
}
