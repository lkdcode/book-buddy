package com.rmsoftmissionlkdcode.bookbuddy.module.loan.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository.LoanRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({
        "/bulk/user-bulk-data.sql"
        , "/bulk/book-bulk-data.sql"
})
class LoanCommandApiTest extends BaseApiTest {
    private static final String URL_PREFIX = "/api/loans";
    private static final String LOAN_BOOK_URL = URL_PREFIX + "/{bookId}";
    private static final String RETURN_BOOK_URL = URL_PREFIX + "/{bookId}/returned/{loanId}";

    private static final Long BOOK_ID = 2L;
    private static final String BOOK_TITLE = "이것이 자바다";
    private static final String BOOK_AUTHOR = "신용권";

    private static final String USER_EMAIL = "test1@test.com";
    private static final String OTHER_USER_EMAIL = "test2@test.com";


    @Autowired
    private LoanRepository loanRepository;

    private Long loanId;

    @BeforeEach
    void setLoanRepository() {
        this.loanRepository.deleteAll();
    }

    @Test
    @DisplayName("도서 대출에 성공할 것이다.")
    void shouldSuccessFullyCheckOutBook() throws Exception {
        // given
        // when
        // then
        checkOutBook();
    }

    @Test
    @DisplayName("도서 반납에 성공할 것이다.")
    void shouldSuccessFullyCheckInBook() throws Exception {
        // given
        checkOutBook();
        LoanRequestDTO.CheckInDTO request = LoanRequestDTO.CheckInDTO.builder()
                .userEmail(USER_EMAIL)
                .build();
        // when

        mockMvc.perform(post(RETURN_BOOK_URL, BOOK_ID, loanId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());
        // then
    }

    private void checkOutBook() throws Exception {
        // given
        LoanRequestDTO.CheckOutDTO request = LoanRequestDTO.CheckOutDTO.builder()
                .userEmail(USER_EMAIL)
                .build();

        // when
        // then
        mockMvc.perform(post(LOAN_BOOK_URL, BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").exists())
                .andExpect(jsonPath("$.userEmail").value(USER_EMAIL))
                .andExpect(jsonPath("$.title").value(BOOK_TITLE))
                .andExpect(jsonPath("$.author").value(BOOK_AUTHOR))
                .andExpect(jsonPath("$.borrowedAt").exists())
        ;

        List<Loan> loanList = loanRepository.findAll();
        Loan loan = loanList.get(0);

        assertThat(loan.getUser().getEmail())
                .isEqualTo(USER_EMAIL);

        assertThat(loan.getBook().getTitle())
                .isEqualTo(BOOK_TITLE);

        assertThat(loan.getBook().getAuthor())
                .isEqualTo(BOOK_AUTHOR);

        assertThat(loan.getBorrowedAt())
                .isBefore(LocalDateTime.now());

        this.loanId = loan.getId();
    }
}