package com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.enums;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode implements AppErrorCode {
    USER_EMAIL_DUPLICATION_ERROR("이메일이 중복되었습니다.", HttpStatus.CONFLICT),
    NOT_FOUND_USER_BY_EMAIL_ERROR("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
