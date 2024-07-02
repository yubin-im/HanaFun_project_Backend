package com.hanaro.hanafun.host.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
    Optional<HostEntity> findByUserEntityUserId(Long userId);
    HostEntity findHostEntityByUserEntity_UserId(Long userId);
}
