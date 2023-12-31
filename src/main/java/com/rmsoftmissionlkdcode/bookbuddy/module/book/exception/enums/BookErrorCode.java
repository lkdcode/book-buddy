package com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.enums;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookErrorCode implements AppErrorCode {
    BOOK_ISBN_DUPLICATION_ERROR("이미 등록되어 있는 책입니다.", HttpStatus.CONFLICT),
    NOT_FOUND_BOOK_BY_ID_ERROR("존재하지 않는 도서입니다.", HttpStatus.NOT_FOUND),
    NEGATIVE_BOOK_QUANTITY_NOT_ALLOWED_ERROR("도서의 최소 수량은 0권 이상입니다.", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BOOK_QUANTITY_ERROR("빌릴 수 있는 도서가 없습니다.", HttpStatus.NOT_FOUND),
    NO_CHANGE_BOOK_INFORMATION("수정된 내용이 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_ISBN_LENGTH_ERROR("ISBN은 10자리 혹인 13자리 입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
