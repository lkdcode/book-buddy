package com.rmsoftmissionlkdcode.bookbuddy.module.user.exception;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppException;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.enums.UserErrorCode;
import lombok.Getter;

public class UserEmailDuplicationException extends AppException {

    @Getter
    private final String message;
    @Getter
    private final int status;

    public UserEmailDuplicationException(UserErrorCode errorCode) {
        super(errorCode);
        this.message = errorCode.getMessage();
        this.status = errorCode.getHttpStatus().value();
    }
}