package com.hanaro.hanafun.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<List<AccountEntity>> findByUserEntityUserId(Long userId);
}
