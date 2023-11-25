package com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom;

import org.springframework.http.HttpStatus;

public interface AppErrorCode {

    String getMessage();

    HttpStatus getHttpStatus();
}
