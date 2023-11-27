package com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppException;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.enums.LoanErrorCode;
import lombok.Getter;

public class AlreadyReturnedException extends AppException {

    @Getter
    private final String message;
    @Getter
    private final int status;

    public AlreadyReturnedException(LoanErrorCode errorCode) {
        super(errorCode);
        this.message = errorCode.getMessage();
        this.status = errorCode.getHttpStatus().value();
    }
}