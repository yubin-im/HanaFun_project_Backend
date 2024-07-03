package com.hanaro.hanafun.transaction.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class TransactionNotFoundException extends CustomException {
    static String MESSAGE = "TRANSACTION_NOT_FOUND";

    public TransactionNotFoundException() {
        super(MESSAGE);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
