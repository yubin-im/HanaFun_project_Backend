package com.hanaro.hanafun.host.domain;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.common.domain.BaseEntity;
import com.hanaro.hanafun.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "host")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HostEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_id")
    private Long hostId;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "introduction", nullable = false)
    private String introduction;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;
}
