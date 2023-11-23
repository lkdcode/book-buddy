package com.rmsoftmissionlkdcode.bookbuddy.global.exception.handler;

import com.rmsoftmissionlkdcode.bookbuddy.global.exception.custom.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        log.error("@Valid Exception : {}", defaultMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(defaultMessage);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> catchAppException(AppException e) {
        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        String message = e.getMessage();
        String exceptionName = e.getClass().getSimpleName();

        log.error("{} status : {}. message : {}", exceptionName, httpStatus, message);

        return ResponseEntity
                .status(httpStatus)
                .body(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> catchRuntimeException(RuntimeException e) {
        String message = e.getMessage();
        log.error("runtimeException message : {}", message);
        log.error("runtimeException fillInStackTrace : {}", e.fillInStackTrace().toString());
        log.error("runtimeException : {}", e.toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body("알 수 없는 예외 상황 : " + message);
    }
}
