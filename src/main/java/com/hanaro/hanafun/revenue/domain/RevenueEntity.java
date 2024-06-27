package com.hanaro.hanafun.revenue.domain;

import com.hanaro.hanafun.common.domain.BaseEntity;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "revenue")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RevenueEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revenue_id")
    private Long revenueId;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private LessonEntity lessonEntity;

    @Column(name = "revenue", nullable = false)
    private Long revenue;

    @Column(name = "material_price", nullable = false)
    @ColumnDefault("0")
    private Integer materialPrice;

    @Column(name = "rental_price", nullable = false)
    @ColumnDefault("0")
    private Integer rentalPrice;

    @Column(name = "etc_price", nullable = false)
    @ColumnDefault("0")
    private Integer etcPrice;
}
