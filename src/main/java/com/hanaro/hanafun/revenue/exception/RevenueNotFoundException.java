package com.hanaro.hanafun.revenue.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class RevenueNotFoundException extends CustomException {
    static String MESSAGE = "REVENUE_NOT_FOUND";
    public RevenueNotFoundException(){
        super(MESSAGE);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
