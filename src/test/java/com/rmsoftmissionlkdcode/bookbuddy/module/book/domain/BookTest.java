package com.rmsoftmissionlkdcode.bookbuddy.module.book.domain;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.BookQuantityException;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.InvalidISBNException;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.MinimumBookQuantityRequiredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookTest {
    private static final String VALID_ISBN = "9791163033462";
    private static final String VALID_AUTHOR = "김종관";
    private static final String VALID_TITLE = "Do it! 알고리즘 코딩 테스트 자바 편";
    private static final int VALID_QUANTITY = 3;

    private final String INVALID_ISBN = "9738";
    private final int INVALID_QUANTITY = -3;

    @Test
    @DisplayName("Book Entity 생성에 성공할 것이다.")
    void createBookEntitySuccessTest() {
        // given
        // when
        Book book = getBook();

        // then
        assertThat(book.getAuthor())
                .isEqualTo(VALID_AUTHOR);

        assertThat(book.getTitle())
                .isEqualTo(VALID_TITLE);

        assertThat(book.getQuantity())
                .isEqualTo(VALID_QUANTITY);

        assertThat(book.getISBN())
                .isEqualTo(VALID_ISBN);
    }

    @Test
    @DisplayName("유효하지 않은 ISBN 으로 Book Entity 생성시 익셉션이 발생할 것이다.")
    void createBookEntitySuccessTest1() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Book.builder()
                .ISBN(INVALID_ISBN)
                .title(VALID_TITLE)
                .author(VALID_AUTHOR)
                .quantity(VALID_QUANTITY)
                .build())
                .isInstanceOf(InvalidISBNException.class);
    }

    @Test
    @DisplayName("유효하지 않은 ISBN 으로 Book Entity 생성시 익셉션이 발생할 것이다.")
    void createBookEntityInvalidISBNExceptionTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Book.builder()
                .ISBN(INVALID_ISBN)
                .title(VALID_TITLE)
                .author(VALID_AUTHOR)
                .quantity(VALID_QUANTITY)
                .build())
                .isInstanceOf(InvalidISBNException.class);
    }

    @Test
    @DisplayName("유효하지 않은 수량으로 Book Entity 생성시 익셉션이 발생할 것이다.")
    void createBookEntityInvalidQuantityExceptionTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Book.builder()
                .quantity(INVALID_QUANTITY)
                .ISBN(VALID_ISBN)
                .title(VALID_TITLE)
                .author(VALID_AUTHOR)
                .build())
                .isInstanceOf(MinimumBookQuantityRequiredException.class);
    }

    @Test
    @DisplayName("lendBook 메서드는 책의 수량을 1 감소 시킬 것이다.")
    void lendBookShouldDecreaseQuantityByOne() {
        // given
        Book book = getBook();
        // when
        book.lendBook();
        // then
        assertThat(book.getQuantity())
                .isEqualTo(VALID_QUANTITY - 1);
    }

    @Test
    @DisplayName("책의 수량이 0일 때 lendBook 메서드 호출은 익셉션을 발생시킬 것이다.")
    void lendBookWithZeroQuantityShouldThrowExceptionTest() {
        // given
        Book book = Book.builder()
                .title(VALID_TITLE)
                .author(VALID_AUTHOR)
                .ISBN(VALID_ISBN)
                .quantity(0)
                .build();
        // when
        // then
        assertThatThrownBy(book::lendBook)
                .isInstanceOf(BookQuantityException.class);
    }

    @Test
    @DisplayName("returnBook 메서드는 책의 수량을 1 증가 시킬 것이다.")
    void returnBookShouldIncreaseQuantityByOne() {
        // given
        Book book = getBook();
        // when
        book.returnBook();
        // then
        assertThat(book.getQuantity())
                .isEqualTo(VALID_QUANTITY + 1);
    }

    @Test
    @DisplayName("제목 수정에 성공할 것이다.")
    void changeTitleSuccessTest() {
        // given
        String updateTitle = "수정한 제목";
        // when
        Book book = getBook();
        book.updateTitle(updateTitle);
        // then
        assertThat(book.getTitle())
                .isEqualTo(updateTitle);
    }

    @Test
    @DisplayName("저자 수정에 성공할 것이다.")
    void changeAuthorSuccessTest() {
        // given
        String updateAuthor = "수정한 저자";
        // when
        Book book = getBook();
        book.updateAuthor(updateAuthor);
        // then
        assertThat(book.getAuthor())
                .isEqualTo(updateAuthor);
    }

    @Test
    @DisplayName("ISBN 수정에 성공할 것이다.")
    void changeISBNSuccessTest() {
        // given
        String updateISBN = "1234567890";
        // when
        Book book = getBook();
        book.updateISBN(updateISBN);
        // then
        assertThat(book.getISBN())
                .isEqualTo(updateISBN);
    }

    @Test
    @DisplayName("Quantity 수정에 성공할 것이다.")
    void changeQuantitySuccessTest() {
        // given
        int updateQuantity = 5;
        // when
        Book book = getBook();
        book.updateQuantity(updateQuantity);
        // then
        assertThat(book.getQuantity())
                .isEqualTo(updateQuantity);
    }

    private static Book getBook() {
        return Book.builder()
                .title(VALID_TITLE)
                .author(VALID_AUTHOR)
                .quantity(VALID_QUANTITY)
                .ISBN(VALID_ISBN)
                .build();
    }
}