package com.hanaro.hanafun.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPassword(String password);
    Optional<UserEntity> findUserEntityByUserId(Long userId);
}
