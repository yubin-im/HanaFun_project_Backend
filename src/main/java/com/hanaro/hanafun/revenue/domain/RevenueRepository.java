package com.hanaro.hanafun.revenue.domain;

import com.hanaro.hanafun.lesson.domain.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RevenueRepository extends JpaRepository<RevenueEntity, Long> {
    @Query(value = "SELECT SUM(R.revenue) FROM RevenueEntity R WHERE R.lessonEntity = :lessonEntity")
    Long totalRevenueByLessonId(@Param("lessonEntity") LessonEntity lessonEntity);
    Optional<RevenueEntity> findByLessonEntityAndCreatedDateBetween(LessonEntity lessonEntity, LocalDateTime startDateTime, LocalDateTime endDateTime);
    Optional<List<RevenueEntity>> findByLessonEntityLessonIdAndCreatedDateBetween(Long lessonId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
