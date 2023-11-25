package com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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
}