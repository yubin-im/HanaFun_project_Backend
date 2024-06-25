package com.hanaro.hanafun.lessondate.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "LessonDate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@DynamicInsert
public class LessonDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessondateId;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int applicant;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
