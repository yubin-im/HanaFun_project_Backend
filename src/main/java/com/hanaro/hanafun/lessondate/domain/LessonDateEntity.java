package com.hanaro.hanafun.lessondate.domain;

import com.hanaro.hanafun.common.domain.BaseEntity;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lessondate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@DynamicInsert
public class LessonDateEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessondateId;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lessonEntity;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int applicant;

    // 신청인원 업데이트
    public void updateApplicant(int applicant) {
        this.applicant = applicant;
    }
}
