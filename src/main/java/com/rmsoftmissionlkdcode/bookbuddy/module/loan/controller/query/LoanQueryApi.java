package com.rmsoftmissionlkdcode.bookbuddy.module.loan.controller.query;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.query.LoanQueryUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Tag(name = "loan", description = "도서 대출 API")
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanQueryApi {
    private final LoanQueryUsecase loanQueryUsecase;

    @Operation(summary = "도서 대출 Api", description = "도서 대출 내역을 조회합니다.", tags = "loan", responses = {
            @ApiResponse(responseCode = "200", description = "도서 대출 내역 조회에 성공하였습니다.")
    })
    @GetMapping("/{bookId}")
    public void getLoanHistory(
            @PathVariable(name = "bookId") @Min(1) Long bookId
    ) {
        loanQueryUsecase.retrieveFindAllByBookId(bookId);
    }
}
