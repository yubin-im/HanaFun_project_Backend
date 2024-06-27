package com.hanaro.hanafun.lessondate.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonDateRepository extends JpaRepository<LessonDateEntity, Long> {
    List<LessonDateEntity> findLessonDateEntitiesByLessonEntity_LessonId(Long lessonId);
    Optional<LessonDateEntity> findLessonDateEntityByLessondateId(Long lessondateId);
}
