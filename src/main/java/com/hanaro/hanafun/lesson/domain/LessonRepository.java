package com.hanaro.hanafun.lesson.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository {
    Optional<List<LessonEntity>> findByHostEntityHostId(Long hostId);
}
