package com.hanaro.hanafun.lessondate.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonDateRepository extends JpaRepository<LessonDateEntity, Long> {
    List<LessonDateEntity> findLessonDateEntitiesByLessonEntity_LessonId(Long lessonId);
}
