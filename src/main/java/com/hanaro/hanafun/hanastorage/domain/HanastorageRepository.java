package com.hanaro.hanafun.hanastorage.domain;

import com.hanaro.hanafun.transaction.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HanastorageRepository extends JpaRepository<HanastorageEntity, Long> {

    Optional<List<HanastorageEntity>> findByLessondateAndIsDeleted(LocalDate lessondate, Boolean isDeleted);
    Optional<HanastorageEntity> findByTransactionEntity(TransactionEntity transactionEntity);
}
