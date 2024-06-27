package com.hanaro.hanafun.account.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AccountBalanceException extends CustomException {
    static String MESSAGE = "INSUFFICIENT_BALANCE";
    public AccountBalanceException(){
        super(MESSAGE);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
