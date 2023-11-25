package com.rmsoftmissionlkdcode.bookbuddy.support.base;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository.BookRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository.LoanRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY,
        connection = EmbeddedDatabaseConnection.H2)
@Sql({
        "/bulk/user-bulk-data.sql"
        , "/bulk/book-bulk-data.sql"
        , "/bulk/loan-bulk-data.sql"
})
public abstract class BaseRepositoryList {
    private static final int REPOSITORY_SIZE = 10;
    protected static final Long USER_ID = 1L;
    protected static final String USER_EMAIL = "test1@test.com";
    protected static final String USER_NAME = "홍길동";
    protected static final String USER_PASSWORD = "password123";


    protected static final Long BOOK_ID = 6L;
    protected static final String BOOK_ISBN = "9791163033462";
    protected static final String BOOK_TITLE = "Do it! 알고리즘 코딩 테스트 자바 편";
    protected static final String BOOK_AUTHOR = "김종관";
    protected static final int BOOK_QUANTITY = 12;

    protected static final Long LOAN_ID = 6L;
    protected static final Long LOAN_USER_ID = 1L;
    protected static final Long LOAN_BOOK_ID = 6L;

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected LoanRepository loanRepository;
    protected User user;
    protected Book book;
    protected Loan loan;

    @BeforeEach
    void setEntity() {
        this.user = userRepository.findById(USER_ID).orElseThrow();
        this.book = bookRepository.findById(BOOK_ID).orElseThrow();
        this.loan = loanRepository.findById(LOAN_ID).orElseThrow();
    }

    @Test
    @DisplayName("각각의 레파지토리의 데이터는 10개일 것이다.")
    void shouldHaveTenSizeRepositoryList() {
        // given
        // when
        int userRepositorySize = userRepository.findAll().size();
        int bookRepositorySize = bookRepository.findAll().size();
        int loanRepositorySize = loanRepository.findAll().size();
        // then
        assertThat(userRepositorySize)
                .isEqualTo(REPOSITORY_SIZE);

        assertThat(bookRepositorySize)
                .isEqualTo(REPOSITORY_SIZE);

        assertThat(loanRepositorySize)
                .isEqualTo(REPOSITORY_SIZE);
    }
}
