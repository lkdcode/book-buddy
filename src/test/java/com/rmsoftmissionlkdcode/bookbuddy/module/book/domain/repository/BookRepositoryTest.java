package com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY
        , connection = EmbeddedDatabaseConnection.H2)
@Sql("/bulk/book-bulk-data.sql")
@TestPropertySource(locations = "classpath:application-test.yml", properties = "spring.config.name=application-test")
class BookRepositoryTest {
    private final String VALID_ISBN = "9791163033462";
    private final String VALID_AUTHOR = "김종관";
    private final Long VALID_ID = 6L;
    private final String VALID_TITLE = "Do it! 알고리즘 코딩 테스트 자바 편";

    private final String INVALID_ISBN = "9791113033462";
    private final String INVALID_AUTHOR = "박종관";
    private final Long INVALID_ID = 11L;
    private final String INVALID_TITLE = "Do it! 알고리즘 코딩 테스트 파이썬 편";

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("유효한 ISBN 으로 조회 시 True 를 리턴할 것이다.")
    void shouldReturnTrueForValidISBN() {
        // given
        // when
        boolean existsById = bookRepository.existsByISBN(VALID_ISBN);
        // then
        assertThat(existsById)
                .isTrue();
    }

    @Test
    @DisplayName("유효하지 않은 ISBN 으로 조회 시 False 를 리턴할 것이다.")
    void shouldReturnFalseForInvalidISBN() {
        // given
        // when
        boolean existsById = bookRepository.existsByISBN(INVALID_ISBN);

        // then
        assertThat(existsById)
                .isFalse();
    }


}