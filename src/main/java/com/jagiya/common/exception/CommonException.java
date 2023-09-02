package com.jagiya.common.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final int errorCode;

    public CommonException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CommonException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}

