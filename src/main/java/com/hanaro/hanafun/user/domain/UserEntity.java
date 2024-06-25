package com.hanaro.hanafun.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name="User")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="user_name", length = 255, nullable = false)
    private String userName;

    @Column(name="password", length = 20, nullable = false)
    private String password;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "is_host", nullable = false)
    private Boolean isHost;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate;
}
