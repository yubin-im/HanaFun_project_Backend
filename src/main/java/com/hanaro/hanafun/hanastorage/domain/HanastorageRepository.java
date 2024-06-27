package com.hanaro.hanafun.hanastorage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HanastorageRepository extends JpaRepository<HanastorageEntity, Long> {

    List<HanastorageEntity> findByLessondateAndIsDeleted(LocalDate lessondate, Boolean isDeleted);
    Optional<HanastorageEntity> findByTransactionEntityTransactionId(Long transactionId);
}
