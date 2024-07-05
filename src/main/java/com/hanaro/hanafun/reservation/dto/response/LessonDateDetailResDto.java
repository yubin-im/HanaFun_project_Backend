package com.hanaro.hanafun.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDateDetailResDto {
    private int applicant;  // lessondate의 신청인원
    private int capacity;  // lesson의 모집인원
    private List<ReservationPerson> people;
}
