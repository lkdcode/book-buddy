package com.rmsoftmissionlkdcode.bookbuddy.module.loan.controller.query;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.query.LoanQueryUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanQueryApi {
    private final LoanQueryUsecase loanQueryUsecase;


    @GetMapping("/{bookId}")
    public void getLoanHistory(
            @PathVariable(name = "bookId") @Min(1) Long bookId
    ) {
        loanQueryUsecase.retrieveFindAllByBookId(bookId);
    }
}
