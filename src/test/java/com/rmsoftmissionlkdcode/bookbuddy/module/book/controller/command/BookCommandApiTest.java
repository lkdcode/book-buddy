package com.rmsoftmissionlkdcode.bookbuddy.module.book.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository.BookRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class BookCommandApiTest extends BaseApiTest {
    private static final String URL_PREFIX = "/api/books";
    private static final String UPDATE_URL = URL_PREFIX + "/{bookId}";
    private static final String CREATE_URL = URL_PREFIX;
    private static final String BOOK_ISBN = "1234567890";
    private static final String BOOK_TITLE = "title";
    private static final String BOOK_AUTHOR = "author";
    private static final Long BOOK_QUANTITY = 3L;
    @Autowired
    private BookRepository bookRepository;

    private Long bookId;

    @BeforeEach
    void setRepository() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("도서 등록에 성공할 것이다.")
    void shouldSuccessFullyRegisterBook() throws Exception {
        // given
        // when
        // then
        registerBook();
    }

    @Test
    @DisplayName("도서 제목을 입력하지않아 도서 등록에 실패할 것이다.")
    void shouldFailFullyRegisterBookWithoutTitle() throws Exception {
        // given
        String expectedErrorMessage = "책의 제목을 입력해주세요.";
        BookRequestDTO.Create request = BookRequestDTO.Create.builder()
                .ISBN(BOOK_ISBN)
                .author(BOOK_AUTHOR)
                .quantity(BOOK_QUANTITY)
                .build();

        // when
        // then
        mockMvc.perform(post(CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("ISBN을 입력하지않아 도서 등록에 실패할 것이다.")
    void shouldFailFullyRegisterBookWithoutISBN() throws Exception {
        // given
        String expectedErrorMessage = "ISBN 고유 번호를 입력해주세요.";
        BookRequestDTO.Create request = BookRequestDTO.Create.builder()
                .title(BOOK_TITLE)
                .author(BOOK_AUTHOR)
                .quantity(BOOK_QUANTITY)
                .build();

        // when
        // then
        mockMvc.perform(post(CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("도서 저자를 입력하지않아 도서 등록에 실패할 것이다.")
    void shouldFailFullyRegisterBookWithoutAuthor() throws Exception {
        // given
        String expectedErrorMessage = "책의 저자를 입력해주세요.";
        BookRequestDTO.Create request = BookRequestDTO.Create.builder()
                .ISBN(BOOK_ISBN)
                .title(BOOK_TITLE)
                .quantity(BOOK_QUANTITY)
                .build();

        // when
        // then
        mockMvc.perform(post(CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("도서 수량을 입력하지않아 도서 등록에 실패할 것이다.")
    void shouldFailFullyRegisterBookWithoutQuantity() throws Exception {
        // given
        String expectedErrorMessage = "책의 수량을 입력해주세요.";
        BookRequestDTO.Create request = BookRequestDTO.Create.builder()
                .ISBN(BOOK_ISBN)
                .title(BOOK_TITLE)
                .author(BOOK_AUTHOR)
                .build();

        // when
        // then
        mockMvc.perform(post(CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("도서 수량을 음수로 입력하면 도서 등록에 실패할 것이다.")
    void shouldFailFullyRegisterBookNegativeQuantity() throws Exception {
        // given
        String expectedErrorMessage = "책의 수량은 음수가 될 수 없습니다.";
        BookRequestDTO.Create request = BookRequestDTO.Create.builder()
                .ISBN(BOOK_ISBN)
                .title(BOOK_TITLE)
                .author(BOOK_AUTHOR)
                .quantity(-1L)
                .build();

        // when
        // then
        mockMvc.perform(post(CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("등록된 도서 수정에 성공할 것이다.")
    void shouldSuccessFullyUpdateBook() throws Exception {
        // given
        registerBook();

        String updateISBN = "1234123412";
        String updateTitle = "updateTitle";
        String updateAuthor = "updateAuthor";
        Long updateQuantity = 2L;

        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .ISBN(updateISBN)
                .title(updateTitle)
                .author(updateAuthor)
                .quantity(updateQuantity)
                .build();

        // when
        // then
        mockMvc.perform(patch(UPDATE_URL, bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Book book = bookRepository.findById(bookId).orElseThrow();

        assertThat(book.getISBN())
                .isEqualTo(updateISBN);

        assertThat(book.getTitle())
                .isEqualTo(updateTitle);

        assertThat(book.getAuthor())
                .isEqualTo(updateAuthor);

        assertThat(book.getQuantity())
                .isEqualTo(updateQuantity);
    }

    @Test
    @DisplayName("등록된 도서의 제목만 수정에 성공할 것이다.")
    void shouldSuccessFullyUpdateBookForTitle() throws Exception {
        // given
        registerBook();

        String updateTitle = "updateTitle";

        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .title(updateTitle)
                .build();

        // when
        // then
        mockMvc.perform(patch(UPDATE_URL, bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Book book = bookRepository.findById(bookId).orElseThrow();

        assertThat(book.getISBN())
                .isEqualTo(BOOK_ISBN);

        assertThat(book.getTitle())
                .isEqualTo(updateTitle);

        assertThat(book.getAuthor())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(book.getQuantity())
                .isEqualTo(BOOK_QUANTITY);
    }

    @Test
    @DisplayName("등록된 도서의 ISBN만 수정에 성공할 것이다.")
    void shouldSuccessFullyUpdateBookForISBN() throws Exception {
        // given
        registerBook();

        String updateISBN = "1234123412";

        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .ISBN(updateISBN)
                .build();

        // when
        // then
        mockMvc.perform(patch(UPDATE_URL, bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Book book = bookRepository.findById(bookId).orElseThrow();

        assertThat(book.getISBN())
                .isEqualTo(updateISBN);

        assertThat(book.getTitle())
                .isEqualTo(BOOK_TITLE);

        assertThat(book.getAuthor())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(book.getQuantity())
                .isEqualTo(BOOK_QUANTITY);
    }

    @Test
    @DisplayName("등록된 도서의 저자만 수정에 성공할 것이다.")
    void shouldSuccessFullyUpdateBookForAuthor() throws Exception {
        // given
        registerBook();

        String updateAuthor = "updateAuthor";

        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .author(updateAuthor)
                .build();

        // when
        // then
        mockMvc.perform(patch(UPDATE_URL, bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Book book = bookRepository.findById(bookId).orElseThrow();

        assertThat(book.getAuthor())
                .isEqualTo(updateAuthor);

        assertThat(book.getTitle())
                .isEqualTo(BOOK_TITLE);

        assertThat(book.getISBN())
                .isEqualTo(BOOK_ISBN);

        assertThat(book.getQuantity())
                .isEqualTo(BOOK_QUANTITY);
    }

    @Test
    @DisplayName("등록된 도서의 수량만 수정에 성공할 것이다.")
    void shouldSuccessFullyUpdateBookForQuantity() throws Exception {
        // given
        registerBook();

        Long updateQuantity = 2L;

        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .quantity(updateQuantity)
                .build();

        // when
        // then
        mockMvc.perform(patch(UPDATE_URL, bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Book book = bookRepository.findById(bookId).orElseThrow();

        assertThat(book.getQuantity())
                .isEqualTo(updateQuantity);

        assertThat(book.getTitle())
                .isEqualTo(BOOK_TITLE);

        assertThat(book.getISBN())
                .isEqualTo(BOOK_ISBN);

        assertThat(book.getAuthor())
                .isEqualTo(BOOK_AUTHOR);
    }


    @Test
    @DisplayName("수정할 내용이 없다면 수정 요청이 실패할 것이다.")
    void shouldFailFullyUpdateBookWithoutAllValues() throws Exception {
        // given
        registerBook();

        String expectedErrorMessage = "수정된 내용이 없습니다.";
        BookRequestDTO.Update request = BookRequestDTO.Update.builder()
                .build();

        // when
        // then
        mockMvc.perform(patch(UPDATE_URL, bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    private void registerBook() throws Exception {
        // given
        BookRequestDTO.Create request = BookRequestDTO.Create.builder()
                .ISBN(BOOK_ISBN)
                .title(BOOK_TITLE)
                .author(BOOK_AUTHOR)
                .quantity(BOOK_QUANTITY)
                .build();

        // when
        // then
        mockMvc.perform(post(CREATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ISBN").value(BOOK_ISBN))
                .andExpect(jsonPath("$.title").value(BOOK_TITLE))
                .andExpect(jsonPath("$.author").value(BOOK_AUTHOR))
                .andExpect(jsonPath("$.quantity").value(BOOK_QUANTITY));

        List<Book> bookList = bookRepository.findAll();
        Book book = bookList.get(0);

        assertThat(book.getISBN())
                .isEqualTo(BOOK_ISBN);

        assertThat(book.getTitle())
                .isEqualTo(BOOK_TITLE);

        assertThat(book.getAuthor())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(book.getQuantity())
                .isEqualTo(BOOK_QUANTITY);

        this.bookId = book.getId();
    }
}