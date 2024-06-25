package com.hanaro.hanafun.reservation.domain;

import com.hanaro.hanafun.lessondate.domain.LessonDate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@DynamicInsert
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

    @ColumnDefault("0")
    @Column(nullable = false)
    private int applicant;

    @ColumnDefault("0")
    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
