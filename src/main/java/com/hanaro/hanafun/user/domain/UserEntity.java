package com.hanaro.hanafun.user.domain;

import com.hanaro.hanafun.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="password", length = 20, nullable = false)
    private String password;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "is_host", nullable = false)
    private Boolean isHost;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}
