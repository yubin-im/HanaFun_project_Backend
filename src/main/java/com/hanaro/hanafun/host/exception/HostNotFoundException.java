package com.hanaro.hanafun.host.exception;

import com.hanaro.hanafun.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class HostNotFoundException extends CustomException {
    static String MESSAGE = "HOST_NOT_FOUND";

    public HostNotFoundException(){
        super(MESSAGE);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
