package com.rmsoftmissionlkdcode.bookbuddy.module.book.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseRepositoryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BookResponseMapperTest extends BaseRepositoryList {
    private Book invalidBook;

    @BeforeEach
    void setBook() {
        this.invalidBook = Book.builder()
                .ISBN("1234567890")
                .build();
    }

    @Test
    @DisplayName("유효한 Book Entity 를 CreateDTO 변환에 성공할 것이다.")
    void convertValidBookEntityToCreateDTOSuccessTest() {
        // given
        // when
        BookResponseDTO.Create createDTO = BookResponseMapper.INSTANCE.bookToCreateDTO(super.book);

        // then
        assertThat(createDTO.author())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(createDTO.title())
                .isEqualTo(BOOK_TITLE);

        assertThat(createDTO.ISBN())
                .isEqualTo(BOOK_ISBN);

        assertThat(createDTO.quantity())
                .isEqualTo(BOOK_QUANTITY);
    }

    @Test
    @DisplayName("유효하지 Book Entity 도 CreateDTO 변환에 성공할 것이며, 값은 null 일 것이다.")
    void convertInvalidBookEntityToCreateDTOSuccessTest() {
        // given
        // when
        BookResponseDTO.Create createDTO = BookResponseMapper.INSTANCE.bookToCreateDTO(invalidBook);

        // then
        assertThat(createDTO.author())
                .isNull();

        assertThat(createDTO.title())
                .isNull();

        assertThat(createDTO.quantity())
                .isZero();
    }

    @Test
    @DisplayName("유효한 Book Entity 를 UpdateDTO 변환에 성공할 것이다.")
    void convertValidBookEntityToUpdateDTOSuccessTest() {
        // given
        // when
        BookResponseDTO.Update updateDTO = BookResponseMapper.INSTANCE.bookToUpdateDTO(super.book);

        // then
        assertThat(updateDTO.author())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(updateDTO.title())
                .isEqualTo(BOOK_TITLE);

        assertThat(updateDTO.ISBN())
                .isEqualTo(BOOK_ISBN);

        assertThat(updateDTO.quantity())
                .isEqualTo(BOOK_QUANTITY);
    }

    @Test
    @DisplayName("유효하지 Book Entity 도 UpdateDTO 변환에 성공할 것이며, 값은 null 일 것이다.")
    void convertInvalidBookEntityToUpdateDTOSuccessTest() {
        // given
        // when
        BookResponseDTO.Update updateDTO = BookResponseMapper.INSTANCE.bookToUpdateDTO(invalidBook);

        // then
        assertThat(updateDTO.author())
                .isNull();

        assertThat(updateDTO.title())
                .isNull();

        assertThat(updateDTO.quantity())
                .isZero();
    }
}