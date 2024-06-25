package com.hanaro.hanafun.reservation.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lessondate_id", nullable = false)
    private LessonDate lessonDate;

    private int applicant;

    private boolean isDeleted;
}
