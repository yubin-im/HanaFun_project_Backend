package com.hanaro.hanafun.lessondate.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonDateRepository extends JpaRepository<LessonDateEntity, Long> {

}
