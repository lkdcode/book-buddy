package com.rmsoftmissionlkdcode.bookbuddy.module.book.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseRepositoryList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookRequestMapperTest extends BaseRepositoryList {

    @Test
    @DisplayName("유효한 Create DTO 를 Book Entity 변환에 성공할 것이다.")
    void convertValidCreateDTOToEntitySuccessTest() {
        // given
        String validTitle = "testTitle";
        String validISBN = "1234567890";
        String validAuthor = "tester";
        int validQuantity = 3;

        BookRequestDTO.Create validCreateDTO = BookRequestDTO.Create.builder()
                .title(validTitle)
                .ISBN(validISBN)
                .author(validAuthor)
                .quantity(validQuantity)
                .build();

        // when
        Book book = BookRequestMapper.INSTANCE.createDTOToBook(validCreateDTO);

        // then
        assertThat(book.getTitle())
                .isEqualTo(validTitle);

        assertThat(book.getISBN())
                .isEqualTo(validISBN);

        assertThat(book.getAuthor())
                .isEqualTo(validAuthor);

        assertThat(book.getQuantity())
                .isEqualTo(validQuantity);
    }

    @Test
    @DisplayName("유효하지 않 Create DTO 를 Book Entity 변환에 익셉션이 발생할 것이다.")
    void convertInvalidCreateDTOToEntityExceptionTest() {
        // given
        BookRequestDTO.Create validCreateDTO = BookRequestDTO.Create.builder()
                .build();
        // when
        // then
        assertThatThrownBy(() -> BookRequestMapper.INSTANCE.createDTOToBook(validCreateDTO))
                .isInstanceOf(NullPointerException.class);
    }
}