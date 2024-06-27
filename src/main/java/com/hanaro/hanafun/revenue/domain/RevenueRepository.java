package com.hanaro.hanafun.revenue.domain;

import com.hanaro.hanafun.lesson.domain.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RevenueRepository extends JpaRepository<RevenueEntity, Long> {
    Optional<RevenueEntity> findByCreatedDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query(value = "SELECT SUM(R.revenue) FROM RevenueEntity R WHERE R.lessonEntity = :lessonEntity")
    Long yearRevenueByLessonId(@Param("lessonEntity") LessonEntity lessonEntity);
}
