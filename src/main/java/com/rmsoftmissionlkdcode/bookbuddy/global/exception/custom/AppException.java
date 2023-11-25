package com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom;

import lombok.Getter;

public class AppException extends RuntimeException {

    @Getter
    private final AppErrorCode errorCode;

    public AppException(AppErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
