package com.hanaro.hanafun.reservation.domain;

import com.hanaro.hanafun.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findReservationEntitiesByUserEntity(UserEntity userEntity);
    List<ReservationEntity> findReservationEntitiesByLessonDateEntity_LessondateId(Long lessondateId);
    Optional<ReservationEntity> findReservationEntityByUserEntity_UserIdAndLessonDateEntity_LessondateId(Long userId, Long lessondateId);
    Optional<ReservationEntity> findByUserEntityUserIdAndLessonDateEntityLessondateId(Long userId, Long lessondateId);
}
