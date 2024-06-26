package com.hanaro.hanafun.host.domain;

import com.hanaro.hanafun.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<HostEntity, Long> {
    HostEntity findHostEntityByUserEntity_UserId(Long userId);
}
