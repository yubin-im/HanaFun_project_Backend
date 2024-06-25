package com.hanaro.hanafun.reservation.controller;

import com.hanaro.hanafun.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

}
