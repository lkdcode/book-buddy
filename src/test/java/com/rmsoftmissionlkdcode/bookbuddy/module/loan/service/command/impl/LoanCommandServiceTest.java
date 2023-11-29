package com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command.impl;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository.BookRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.BookNotFoundException;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository.LoanRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.NotFoundLoanHistoryException;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command.LoanCommandUsecase;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.repository.UserRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.NotFoundUserByEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml", properties = "spring.config.name=application-test")
class LoanCommandServiceTest {
    private static final int ONE_SECOND = 1;
    private static final String USER_EMAIL = "test@test.com";
    private static final String USER_NAME = "name";
    private static final String USER_PASSWORD = "password123";
    private static final String BOOK_TITLE = "book title";
    private static final String BOOK_AUTHOR = "book author";
    private static final String BOOK_ISBN = "1234567890";
    private static final Long BOOK_QUANTITY = 1L;

    @Autowired
    private LoanCommandUsecase loanCommandUsecase;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    private Long bookId;
    private Long loanId;

    @BeforeEach
    void setRepository() {
        User user = userRepository.save(User.builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .name(USER_NAME)
                .build());

        Book book = bookRepository.save(Book.builder()
                .ISBN(BOOK_ISBN)
                .title(BOOK_TITLE)
                .author(BOOK_AUTHOR)
                .quantity(BOOK_QUANTITY)
                .build());

        Loan loan = loanRepository.save(Loan.builder()
                .book(book)
                .user(user)
                .build());

        this.bookId = book.getId();
        this.loanId = loan.getId();
    }

    @Test
    @DisplayName("도서 대출에 성공할 것이다.")
    void shouldSuccessFullyLoanBook() {
        // given
        LoanRequestDTO.CheckOutDTO request = LoanRequestDTO.CheckOutDTO.builder()
                .userEmail(USER_EMAIL)
                .build();

        // when
        LoanResponseDTO.Borrowed response = loanCommandUsecase.executeLoanBookToUser(bookId, request);

        // then
        assertThat(response.author())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(response.title())
                .isEqualTo(BOOK_TITLE);

        assertThat(response.userEmail())
                .isEqualTo(USER_EMAIL);

        assertThat(response.borrowedAt())
                .isCloseTo(LocalDateTime.now(), within(ONE_SECOND, ChronoUnit.SECONDS));
    }

    @Test
    @DisplayName("유효하지 않은 BookId 로 도서 대출 시 익셉션이 발생할 것이다.")
    void shouldThrowExceptionInvalidBookId() {
        // given
        LoanRequestDTO.CheckOutDTO request = LoanRequestDTO.CheckOutDTO.builder()
                .userEmail(USER_EMAIL)
                .build();
        // when

        // then
        assertThatThrownBy(() -> loanCommandUsecase.executeLoanBookToUser(0L, request))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    @DisplayName("유효하지 않은 UserEmail 로 도서 대출 시 익셉션이 발생할 것이다.")
    void shouldThrowExceptionInvalidUserEmail() {
        // given
        String invalidEmail = "invalid@test.com";
        LoanRequestDTO.CheckOutDTO request = LoanRequestDTO.CheckOutDTO.builder()
                .userEmail(invalidEmail)
                .build();
        // when

        // then
        assertThatThrownBy(() -> loanCommandUsecase.executeLoanBookToUser(bookId, request))
                .isInstanceOf(NotFoundUserByEmailException.class);
    }

    @Test
    @DisplayName("도서 반납에 성공할 것이다.")
    void shouldSuccessFullyReturnBook() {
        // given
        LoanRequestDTO.CheckInDTO request = LoanRequestDTO.CheckInDTO.builder()
                .userEmail(USER_EMAIL)
                .build();

        // when
        LoanResponseDTO.Returned response = loanCommandUsecase.executeReturnBook(loanId, bookId, request);

        // then
        assertThat(response.author())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(response.title())
                .isEqualTo(BOOK_TITLE);

        assertThat(response.userEmail())
                .isEqualTo(USER_EMAIL);

        assertThat(response.returnedAt())
                .isCloseTo(LocalDateTime.now(), within(ONE_SECOND, ChronoUnit.SECONDS));

        assertThat(response.borrowedAt())
                .isBefore(response.returnedAt());
    }

    @Test
    @DisplayName("유효하지 않은 UserEmail 로 도서 반납 시 익셉션이 발생할 것이다.")
    void shouldThrowExceptionForInvalidUserEmailOnBookReturn() {
        // given
        String invalidEmail = "invalid@test.com";
        LoanRequestDTO.CheckInDTO request = LoanRequestDTO.CheckInDTO.builder()
                .userEmail(invalidEmail)
                .build();

        // when
        // then
        assertThatThrownBy(() -> loanCommandUsecase.executeReturnBook(loanId, bookId, request))
                .isInstanceOf(NotFoundUserByEmailException.class);
    }

    @Test
    @DisplayName("유효하지 않은 loanId 로 도서 반납 시 익셉션이 발생할 것이다.")
    void shouldThrowExceptionForInvalidLoanIdOnBookReturn() {
        // given
        Long invalidLoanId = 0L;
        LoanRequestDTO.CheckInDTO request = LoanRequestDTO.CheckInDTO.builder()
                .userEmail(USER_EMAIL)
                .build();

        // when
        // then
        assertThatThrownBy(() -> loanCommandUsecase.executeReturnBook(invalidLoanId, bookId, request))
                .isInstanceOf(NotFoundLoanHistoryException.class);
    }

    @Test
    @DisplayName("유효하지 않은 bookId 로 도서 반납 시 익셉션이 발생할 것이다.")
    void shouldThrowExceptionInvalidBookIdOnBookReturn() {
        // given
        Long invalidBookId = 0L;
        LoanRequestDTO.CheckInDTO request = LoanRequestDTO.CheckInDTO.builder()
                .userEmail(USER_EMAIL)
                .build();

        // when
        // then
        assertThatThrownBy(() -> loanCommandUsecase.executeReturnBook(loanId, invalidBookId, request))
                .isInstanceOf(BookNotFoundException.class);
    }
}