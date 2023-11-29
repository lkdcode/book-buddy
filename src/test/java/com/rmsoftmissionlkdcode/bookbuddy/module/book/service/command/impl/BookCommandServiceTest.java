package com.rmsoftmissionlkdcode.bookbuddy.module.book.service.command.impl;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository.BookRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.NoChangesToApplyException;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.service.command.BookCommandUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml", properties = "spring.config.name=application-test")
class BookCommandServiceTest {
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    private static final String ISBN = "1234567890";
    private static final Long QUANTITY = 3L;
    @Autowired
    private BookCommandUsecase bookCommandUsecase;
    @Autowired
    private BookRepository bookRepository;

    private BookResponseDTO.Create response;
    private Long bookId;

    @BeforeEach
    void setResponse() {
        this.response = executeRegisterBook();
        bookId = bookRepository.findByISBN(ISBN).getId();
    }

    @Test
    @DisplayName("유효한 리퀘스트로 도서 저장에 성공할 것이다.")
    void shouldCreateSuccessFullyWithValidRequest() {
        // given
        // when
        // then
        assertThat(response.title())
                .isEqualTo(TITLE);

        assertThat(response.author())
                .isEqualTo(AUTHOR);

        assertThat(response.ISBN())
                .isEqualTo(ISBN);

        assertThat(response.quantity())
                .isEqualTo(QUANTITY);
    }

    @Test
    @DisplayName("유효하지 않은 리퀘스트 요청으로 도서 저장시 익셉션이 발생할 것이다.")
    void shouldThrowExceptionWhenCreateRequestMissingISBN() {
        // given
        String author = "author";
        String title = "title";
        Long quantity = 3L;

        BookRequestDTO.Create invalidRequest = BookRequestDTO.Create.builder()
                .author(author)
                .title(title)
                .quantity(quantity)
                .build();

        // when
        // then
        assertThatThrownBy(() -> bookCommandUsecase.executeRegisterBook(invalidRequest))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("업데이트 리퀘스트의 바뀐 내용이 없다면 익셉션이 발생할 것이다.")
    void shouldThrowExceptionIfNoChangesApplyTest() {
        // given
        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .title(TITLE)
                .author(AUTHOR)
                .ISBN(ISBN)
                .quantity(QUANTITY)
                .build();

        // when
        // then
        assertThatThrownBy(() -> bookCommandUsecase.executeUpdateBook(bookId, request))
                .isInstanceOf(NoChangesToApplyException.class);
    }

    @Test
    @DisplayName("업데이트 리퀘스트의 값이 모두 null 이라면 익셉션이 발생할 것이다.")
    void shouldThrowExceptionIfAllValuesInUpdateRequestAreNull() {
        // given
        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .build();

        // when
        // then
        assertThatThrownBy(() -> bookCommandUsecase.executeUpdateBook(bookId, request))
                .isInstanceOf(NoChangesToApplyException.class);
    }

    @Test
    @DisplayName("도서의 제목만 수정할 것이다.")
    void shouldSuccessFullyChangeBookTitleTest() {
        // given
        String changeTitle = "change title";
        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .title(changeTitle)
                .build();

        // when
        // then
        BookResponseDTO.Update response = bookCommandUsecase.executeUpdateBook(bookId, request);
        assertThat(response.title())
                .isEqualTo(changeTitle);

        assertThat(response.ISBN())
                .isEqualTo(ISBN);

        assertThat(response.author())
                .isEqualTo(AUTHOR);

        assertThat(response.quantity())
                .isEqualTo(QUANTITY);
    }

    @Test
    @DisplayName("도서의 저자만 수정할 것이다.")
    void shouldSuccessFullyChangeBookAuthorTest() {
        // given
        String changeAuthor = "change title";
        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .author(changeAuthor)
                .build();

        // when
        // then
        BookResponseDTO.Update response = bookCommandUsecase.executeUpdateBook(bookId, request);
        assertThat(response.author())
                .isEqualTo(changeAuthor);

        assertThat(response.ISBN())
                .isEqualTo(ISBN);

        assertThat(response.title())
                .isEqualTo(TITLE);

        assertThat(response.quantity())
                .isEqualTo(QUANTITY);
    }

    @Test
    @DisplayName("도서의 ISBN만 수정할 것이다.")
    void shouldSuccessFullyChangeBookISBNTest() {
        // given
        String changeISBN = "9876543210";
        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .ISBN(changeISBN)
                .build();

        // when
        // then
        BookResponseDTO.Update response = bookCommandUsecase.executeUpdateBook(bookId, request);
        assertThat(response.ISBN())
                .isEqualTo(changeISBN);

        assertThat(response.author())
                .isEqualTo(AUTHOR);

        assertThat(response.title())
                .isEqualTo(TITLE);

        assertThat(response.quantity())
                .isEqualTo(QUANTITY);
    }

    @Test
    @DisplayName("도서의 수량만 수정할 것이다.")
    void shouldSuccessfullyChangeBookQuantityTest() {
        // given
        Long changeQuantity = 33L;
        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .quantity(changeQuantity)
                .build();

        // when
        // then
        BookResponseDTO.Update response = bookCommandUsecase.executeUpdateBook(bookId, request);
        assertThat(response.quantity())
                .isEqualTo(changeQuantity);

        assertThat(response.ISBN())
                .isEqualTo(ISBN);

        assertThat(response.author())
                .isEqualTo(AUTHOR);

        assertThat(response.title())
                .isEqualTo(TITLE);

    }

    private BookResponseDTO.Create executeRegisterBook() {
        BookRequestDTO.Create request = BookRequestDTO.Create.builder()
                .author(AUTHOR)
                .title(TITLE)
                .ISBN(ISBN)
                .quantity(QUANTITY)
                .build();

        return bookCommandUsecase.executeRegisterBook(request);
    }
}