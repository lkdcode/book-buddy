package com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.enums;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LoanErrorCode implements AppErrorCode {
    INVALID_RETURN_USER_ERROR("반납 대상자가 아닙니다.", HttpStatus.BAD_REQUEST),
    INVALID_RETURN_BOOK_ERROR("반납 도서가 아닙니다.", HttpStatus.BAD_REQUEST),
    NON_EXISTENT_LOAN_ID_ERROR("도서 대출 내역이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_RETURN_ERROR("이미 반납된 도서입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
