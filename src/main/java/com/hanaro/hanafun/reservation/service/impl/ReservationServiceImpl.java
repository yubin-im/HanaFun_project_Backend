package com.hanaro.hanafun.reservation.service.impl;

import com.hanaro.hanafun.reservation.domain.ReservationRepository;
import com.hanaro.hanafun.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

}
