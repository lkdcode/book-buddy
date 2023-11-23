package com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.enums;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookErrorCode implements AppErrorCode {
    BOOK_ISBN_DUPLICATION_ERROR("이미 등록되어 있는 책입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_BOOK_BY_ID_ERROR("존재하지 않는 도서입니다.", HttpStatus.BAD_REQUEST),
    NO_CHANGES_TO_APPLY_ERROR("아이디 혹은 비밀번호를 다시 확인해주세요.", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
