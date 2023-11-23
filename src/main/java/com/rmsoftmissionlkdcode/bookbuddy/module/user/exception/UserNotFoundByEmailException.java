package com.rmsoftmissionlkdcode.bookbuddy.module.user.exception;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppException;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.enums.UserErrorCode;
import lombok.Getter;

public class UserNotFoundByEmailException extends AppException {
    @Getter
    private final String message;

    public UserNotFoundByEmailException(UserErrorCode errorCode) {
        super(errorCode);
        this.message = errorCode.getMessage();
    }
}
