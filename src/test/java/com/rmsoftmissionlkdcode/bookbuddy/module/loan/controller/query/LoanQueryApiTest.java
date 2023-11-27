package com.rmsoftmissionlkdcode.bookbuddy.module.loan.controller.query;

import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({
        "/bulk/user-bulk-data.sql"
        , "/bulk/book-bulk-data.sql"
        , "/bulk/loan-bulk-data.sql"
})
class LoanQueryApiTest extends BaseApiTest {
    private static final String HISTORY_URL = "/api/loans/{bookId}";

    @Test
    @DisplayName("도서 대출 내역 조회에 성공할 것이다.")
    void shouldSuccessFullyGetLoanHistory() throws Exception {
        // given
        Long bookId = 4L;
        String bookTitle = "면접을 위한 CS 전공지식 노트";
        String bookAuthor = "주홍철";

        String userEmailForLoanId5 = "test5@test.com";
        String userEmailForLoanId7 = "test7@test.com";
        String userEmailForLoanId8 = "test8@test.com";

        // when
        // then
        mockMvc.perform(get(HISTORY_URL, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title").value(everyItem(is(bookTitle))))
                .andExpect(jsonPath("$[*].author").value(everyItem(is(bookAuthor))))

                .andExpect(jsonPath("$[0].loanId").value(5))
                .andExpect(jsonPath("$[1].loanId").value(7))
                .andExpect(jsonPath("$[2].loanId").value(8))

                .andExpect(jsonPath("$[0].userEmail").value(userEmailForLoanId5))
                .andExpect(jsonPath("$[1].userEmail").value(userEmailForLoanId7))
                .andExpect(jsonPath("$[2].userEmail").value(userEmailForLoanId8))
        ;
    }
}