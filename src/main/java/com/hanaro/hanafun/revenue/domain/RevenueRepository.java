package com.hanaro.hanafun.revenue.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RevenueRepository extends JpaRepository<RevenueEntity, Long> {
    Optional<RevenueEntity> findByCreatedDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
