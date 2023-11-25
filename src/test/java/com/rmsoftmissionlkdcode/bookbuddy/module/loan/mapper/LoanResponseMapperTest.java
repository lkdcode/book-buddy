package com.rmsoftmissionlkdcode.bookbuddy.module.loan.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseRepositoryList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class LoanResponseMapperTest extends BaseRepositoryList {

    @Test
    @DisplayName("유효한 Loan Entity 를 BorrowedDTO 변환에 성공할 것이다.")
    void convertValidLoanEntityToResponseDTOSuccessTest() {
        // given
        // when
        LoanResponseDTO.Borrowed responseDTO = LoanResponseMapper.INSTANCE.loanToBorrowedDTO(super.loan);

        // then
        assertThat(responseDTO.loanId())
                .isEqualTo(LOAN_ID);

        assertThat(responseDTO.author())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(responseDTO.userEmail())
                .isEqualTo(USER_EMAIL);

        assertThat(responseDTO.title())
                .isEqualTo(BOOK_TITLE);
    }


    @Test
    @DisplayName("유효하지 않은 Loan Entity 를 BorrowedDTO 변환에 성공할 것이며, 값은 null 일 것이다.")
    void convertInvalidLoanEntityToResponseDTOSuccessTest() {
        // given
        Loan invalidLoan = Loan.builder().build();

        // when
        LoanResponseDTO.Borrowed responseDTO = LoanResponseMapper.INSTANCE.loanToBorrowedDTO(invalidLoan);

        // then
        assertThat(responseDTO.loanId())
                .isNull();

        assertThat(responseDTO.author())
                .isNull();

        assertThat(responseDTO.userEmail())
                .isNull();

        assertThat(responseDTO.title())
                .isNull();
    }

    @Test
    @DisplayName("유효한 Loan Entity 를 ReturnedDTO 로 변환에 성공할 것이다.")
    void convertValidLoanEntityToReturnedDTOSuccessTest() {
        // given
        super.loan.returnedAt();

        // when
        LoanResponseDTO.Returned responseDTO = LoanResponseMapper.INSTANCE.loanToReturnedDTO(super.loan);

        // then
        assertThat(responseDTO.author())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(responseDTO.userEmail())
                .isEqualTo(USER_EMAIL);

        assertThat(responseDTO.title())
                .isEqualTo(BOOK_TITLE);

        assertThat(responseDTO.borrowedAt().getHour())
                .isEqualTo(LocalTime.now().getHour());

        assertThat(responseDTO.returnedAt().getHour())
                .isEqualTo(LocalTime.now().getHour());

        assertThat(responseDTO.borrowedAt().getMinute())
                .isEqualTo(LocalTime.now().getMinute());

        assertThat(responseDTO.returnedAt().getMinute())
                .isEqualTo(LocalTime.now().getMinute());
    }

    @Test
    @DisplayName("유효하지 않은 Loan Entity 를 ReturnedDTO 로 변환에 성공할 것이며, 값은 null 일 것이다.")
    void convertInvalidLoanEntityToReturnedDTOSuccessTest() {
        // given
        Loan invalidLoan = Loan.builder().build();

        // when
        LoanResponseDTO.Returned responseDTO = LoanResponseMapper.INSTANCE.loanToReturnedDTO(invalidLoan);

        // then
        assertThat(responseDTO.author())
                .isNull();

        assertThat(responseDTO.userEmail())
                .isNull();

        assertThat(responseDTO.title())
                .isNull();

        assertThat(responseDTO.borrowedAt())
                .isNull();

        assertThat(responseDTO.returnedAt())
                .isNull();
    }

    @Test
    @DisplayName("유효한 Book, User 로 Loan Entity 변환에 성공할 것이다.")
    void convertValidBookAndValidUserToLoanEntitySuccessTest() {
        // given
        // when
        Loan loan = LoanResponseMapper.INSTANCE.createLoanFromBookAndUser(super.book, super.user);

        // then
        assertThat(loan.getUser().getId())
                .isEqualTo(USER_ID);

        assertThat(loan.getUser().getEmail())
                .isEqualTo(USER_EMAIL);

        assertThat(loan.getBook().getTitle())
                .isEqualTo(BOOK_TITLE);

        assertThat(loan.getBook().getId())
                .isEqualTo(BOOK_ID);
    }

    @Test
    @DisplayName("유효하지 않은 Book, User 로 Loan Entity 변환에 성공할 것이며, 값은 null 일 것이다.")
    void convertInvalidBookAndInvalidUserToLoanEntitySuccessTest() {
        // given
        Book invalidBook = Book.builder()
                .ISBN("1234567890")
                .build();
        User invalidUser = User.builder()
                .build();

        // when
        Loan loan = LoanResponseMapper.INSTANCE.createLoanFromBookAndUser(invalidBook, invalidUser);

        // then
        assertThat(loan.getUser().getId())
                .isNull();

        assertThat(loan.getUser().getEmail())
                .isNull();

        assertThat(loan.getBook().getTitle())
                .isNull();

        assertThat(loan.getBook().getId())
                .isNull();
    }
}