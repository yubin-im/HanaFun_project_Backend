package com.hanaro.hanafun.transaction.domain;

import com.hanaro.hanafun.transaction.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByReservationEntityReservationIdAndType(Long reservationId, Type type);
}
