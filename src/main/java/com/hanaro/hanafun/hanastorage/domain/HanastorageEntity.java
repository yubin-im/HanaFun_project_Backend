package com.hanaro.hanafun.hanastorage.domain;

import com.hanaro.hanafun.transaction.domain.TransactionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "hanastorage")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HanastorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hanastorage_id")
    private Long hanastorageId;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private TransactionEntity transactionEntity;

    @Column(name = "lessondate")
    private LocalDate lessondate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
