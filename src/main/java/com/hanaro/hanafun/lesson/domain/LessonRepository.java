package com.hanaro.hanafun.lesson.domain;

import com.hanaro.hanafun.host.domain.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findLessonEntitiesByHostEntity(HostEntity hostEntity);

    Optional<List<LessonEntity>> findByHostEntityHostId(Long hostId);
}
