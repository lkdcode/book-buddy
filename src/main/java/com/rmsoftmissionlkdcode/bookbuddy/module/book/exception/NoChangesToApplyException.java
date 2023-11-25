package com.rmsoftmissionlkdcode.bookbuddy.module.book.exception;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppException;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.enums.BookErrorCode;
import lombok.Getter;

public class NoChangesToApplyException extends AppException {

    @Getter
    private final String message;
    @Getter
    private final int status;

    public NoChangesToApplyException(BookErrorCode errorCode) {
        super(errorCode);
        this.message = errorCode.getMessage();
        this.status = errorCode.getHttpStatus().value();
    }
}