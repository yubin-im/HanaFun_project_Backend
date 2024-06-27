package com.hanaro.hanafun.transaction.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
