package com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository.LoanRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.InvalidReturningBookException;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.InvalidReturningUserException;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class LoanTest {
    private static final int ONE_SECOND = 1;
    private static final String USER_EMAIL = "test1@naver.com";
    private static final String USER_NAME = "홍길동";
    private static final String USER_PASSWORD = "password123";
    private static final String VALID_ISBN = "9791163033462";
    private static final String VALID_AUTHOR = "김종관";
    private static final String VALID_TITLE = "Do it! 알고리즘 코딩 테스트 자바 편";
    private static final Long VALID_QUANTITY = 3L;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private LoanRepository loanRepository;
    private User user;
    private Book book;
    private Loan loan;

    @BeforeEach
    @Transactional
    void initialize() {
        this.user = getUser();
        testEntityManager.persistAndFlush(user);

        this.book = getBook();
        testEntityManager.persistAndFlush(book);

        this.loan = getLoan();
        testEntityManager.persistAndFlush(loan);

        loanRepository.save(loan);
        testEntityManager.flush();
    }

    @Test
    @DisplayName("Loan Entity 저장 시 Loan 이 반환될 것이다.")
    public void whenSaveLoan_thenReturnLoanTest() {
        // given
        // when
        Loan foundLoan = testEntityManager.find(Loan.class, loan.getId());

        // then
        assertThat(foundLoan)
                .isNotNull();

        assertThat(foundLoan.getUser())
                .isEqualTo(user);

        assertThat(foundLoan.getBook())
                .isEqualTo(book);

        assertThat(foundLoan.getBorrowedAt())
                .isNotNull();
    }

    @Test
    @DisplayName("Loan Entity 저장 시 대출 시간이 저장될 것이며, 현재 시간과 1초 이내일 것이다.")
    public void whenBorrowedAt_thenBorrowedAtIsSetTest() {
        // given
        // when
        // then
        assertThat(loan.getBorrowedAt())
                .isNotNull()
                .isCloseTo(LocalDateTime.now(), within(ONE_SECOND, ChronoUnit.SECONDS));
    }

    @Test
    @DisplayName("Loan 도서 반납 메서드 호출 시 반납 시간이 저장될 것이며, 현재 시간과 1초 이내일 것이다.")
    public void whenReturnedAt_thenReturnedAtIsSet() {
        // given
        // when
        loan.returnedAt();

        // then
        assertThat(loan.getReturnedAt())
                .isNotNull()
                .isCloseTo(LocalDateTime.now(), within(ONE_SECOND, ChronoUnit.SECONDS));
    }

    @Test
    @DisplayName("반납할 사용자의 이메일로 반환 메서드 검증 시 익셉션이 발생하지 않을 것이다.")
    public void whenValidateReturnUserWithValidUser_thenNoExceptionTest() {
        // given
        // when, then
        assertThatCode(() -> loan.validateReturnUser(USER_EMAIL))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("다른 사용자의 이메일로 반환 메서드 검증 시 익셉션이 발생할 것이다.")
    public void whenValidateReturnUserWithInvalidUser_thenExceptionTest() {
        // given
        String otherUserEmail = "other@example.com";

        // when, then
        assertThatThrownBy(() -> loan.validateReturnUser(otherUserEmail))
                .isInstanceOf(InvalidReturningUserException.class);
    }

    @Test
    @DisplayName("유효한 Book Id로 반환 메서드 검증 시 예외가 발생하지 않을 것이다.")
    public void whenValidateReturnBookIdWithValidBookId_thenNoExceptionTest() {
        // given
        Long validId = loan.getBook().getId();

        // when, then

        assertThatCode(() -> loan.validateReturnBookId(validId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유효하지 않은 Book Id로 반환 메서드 검증 시 예외가 발생할 것이다.")
    public void whenValidateReturnBookIdWithInvalidBookId_thenExceptionTest() {
        // given
        Long invalidId = -1L;

        // when, then
        assertThatThrownBy(() -> loan.validateReturnBookId(invalidId))
                .isInstanceOf(InvalidReturningBookException.class);
    }

    private Loan getLoan() {
        return Loan.builder()
                .user(user)
                .book(book)
                .build();
    }

    private Book getBook() {
        return Book.builder()
                .title(VALID_TITLE)
                .author(VALID_AUTHOR)
                .quantity(VALID_QUANTITY)
                .ISBN(VALID_ISBN)
                .build();
    }

    private User getUser() {
        return User.builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .name(USER_NAME)
                .build();
    }
}