package com.hanaro.hanafun.lesson.domain;

import com.hanaro.hanafun.category.domain.CategoryEntity;
import com.hanaro.hanafun.common.domain.BaseEntity;
import com.hanaro.hanafun.host.domain.HostEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "lesson")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@DynamicInsert
public class LessonEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private HostEntity hostEntity;

    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoryEntity;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String description;

    private String materials;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int applicantSum;

    @ColumnDefault("0")
    @Column(nullable = false)
    private boolean isDeleted;
}
