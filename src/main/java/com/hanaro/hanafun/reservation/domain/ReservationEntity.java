package com.hanaro.hanafun.reservation.domain;

import com.hanaro.hanafun.common.domain.BaseEntity;
import com.hanaro.hanafun.lessondate.domain.LessonDateEntity;
import com.hanaro.hanafun.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@DynamicInsert
public class ReservationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "lessondate_id", nullable = false)
    private LessonDateEntity lessonDateEntity;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int applicant;

    @ColumnDefault("0")
    @Column(nullable = false)
    private boolean isDeleted;

    // 삭제 업데이트
    public void updateIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
