package com.hanaro.hanafun.transaction.domain;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.common.domain.BaseEntity;
import com.hanaro.hanafun.reservation.domain.ReservationEntity;
import com.hanaro.hanafun.transaction.enums.Type;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long TransactionId;

    @ManyToOne
    @JoinColumn(name = "deposit_id", nullable = false)
    private AccountEntity depositAccount;

    @ManyToOne
    @JoinColumn(name = "withdraw_id", nullable = false)
    private AccountEntity withdrawAccount;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservationEntity;

    @Column(name = "payment", nullable = false)
    private Integer payment;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
}
