package com.hanaro.hanafun.account.domain;

import com.hanaro.hanafun.common.domain.BaseEntity;
import com.hanaro.hanafun.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "account_number", length = 20, nullable = false)
    private String accountNumber;

    @Column(name = "account_name", length = 50)
    private String accountName;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    private Integer balance;

    @Column(name = "qr")
    private String qr;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}
