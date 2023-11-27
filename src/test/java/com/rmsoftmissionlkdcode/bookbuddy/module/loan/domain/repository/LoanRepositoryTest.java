package com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY
        , connection = EmbeddedDatabaseConnection.H2)
@Sql({
        "/bulk/user-bulk-data.sql"
        , "/bulk/book-bulk-data.sql"
        , "/bulk/loan-bulk-data.sql"
})
class LoanRepositoryTest {
    private static final Long VALID_ID = 3L;
    private static final Long INVALID_ID = 33L;
    private static final Long VALID_BOOK_ID = 3L;
    @Autowired
    private LoanRepository loanRepository;

    @Test
    @DisplayName("유효한 Id 로 조회 시 True 를 리턴할 것이다.")
    void shouldReturnTrueForValidId() {
        // given
        // when
        boolean existsById = loanRepository.existsById(VALID_ID);
        // then
        assertThat(existsById)
                .isTrue();
    }

    @Test
    @DisplayName("유효하지 않은 Id 로 조회 시 False 를 리턴할 것이다.")
    void shouldReturnFalseForInvalidId() {
        // given
        // when
        boolean existsById = loanRepository.existsById(INVALID_ID);
        // then
        assertThat(existsById)
                .isFalse();
    }

    @Test
    @DisplayName("대출 내역이 존재하는 BookId 로 대출 내역 조회에 성공할 것이다.")
    void shouldRetrieveLoanHistoryWithValidBookId() {
        // given
        int loanIndex = 0;

        int expectedSize = 2;
        String expectedBookTitle = "오브젝트";
        String expectedBookAuthor = "조영호";
        String expectedBookISBN = "9791158392536";

        String expectedUserEmail = "test3@test.com";
        String expectedUserName = "나길동";

        // when
        List<Loan> loanList = loanRepository.findByBookId(VALID_BOOK_ID);

        // then
        Book book = loanList.get(loanIndex).getBook();
        User user = loanList.get(loanIndex).getUser();

        assertThat(loanList.size())
                .isEqualTo(expectedSize);

        assertThat(book.getTitle())
                .isEqualTo(expectedBookTitle);

        assertThat(book.getAuthor())
                .isEqualTo(expectedBookAuthor);

        assertThat(book.getISBN())
                .isEqualTo(expectedBookISBN);

        assertThat(user.getEmail())
                .isEqualTo(expectedUserEmail);

        assertThat(user.getName())
                .isEqualTo(expectedUserName);
    }
}