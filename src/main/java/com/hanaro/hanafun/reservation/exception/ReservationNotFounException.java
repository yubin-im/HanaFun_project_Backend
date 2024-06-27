package com.hanaro.hanafun.reservation.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ReservationNotFounException extends CustomException {
    static String MESSAGE = "RESERVATION_NOT_FOUND";

    public ReservationNotFounException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
