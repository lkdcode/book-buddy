package com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmsoftmissionlkdcode.bookbuddy.global.common.BaseEntity;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.InvalidReturningBookException;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.InvalidReturningUserException;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.enums.LoanErrorCode;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Entity
@Table(name = "tb_loan")
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Loan extends BaseEntity {

    @CreatedDate
    @Column(name = "borrowed_at", updatable = false)
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime borrowedAt;

    @Column(name = "returned_at")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime returnedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Builder
    public Loan(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public void returned() {
        this.returnedAt = LocalDateTime.now();
    }

    public void validateReturnUser(String userEmail) {
        if (!user.getEmail().equals(userEmail)) {
            throw new InvalidReturningUserException(LoanErrorCode.INVALID_RETURN_USER_ERROR);
        }
    }

    public void validateReturnBookId(Long bookId) {
        if (!book.getId().equals(bookId)) {
            throw new InvalidReturningBookException(LoanErrorCode.INVALID_RETURN_BOOK_ERROR);
        }
    }
}
