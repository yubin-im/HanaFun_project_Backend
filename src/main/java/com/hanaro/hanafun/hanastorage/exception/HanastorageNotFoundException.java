package com.hanaro.hanafun.hanastorage.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class HanastorageNotFoundException extends CustomException {
    static String MESSAGE = "HANASTORAGE_NOT_FOUND";

    public HanastorageNotFoundException(){
        super(MESSAGE);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
