package com.hanaro.hanafun.host.domain;

import com.hanaro.hanafun.account.domain.AccountEntity;
import com.hanaro.hanafun.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name = "Host")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class HostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_id")
    private Long hostId;

    @JoinColumn(name = "user_id")
    @OneToOne(optional = false)
    private UserEntity userEntity;

    @Column(name = "introduction", length = 255, nullable = false)
    private String introduction;

    @Column(name = "account_id")
    @OneToOne(optional = false)
    private AccountEntity accountEntity;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate;
}
