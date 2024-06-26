package com.hanaro.hanafun.account.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends CustomException {
    static String MESSAGE = "ACCOUNT_NOT_FOUND";

    public AccountNotFoundException(){
        super(MESSAGE);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
