package com.rmsoftmissionlkdcode.bookbuddy.module.loan.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command.LoanCommandUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanCommandApi {
    private final LoanCommandUsecase loanCommandUsecase;

    @PostMapping("/{bookId}")
    public LoanResponseDTO.Borrowed getLoanBookToUser(
            @PathVariable(name = "bookId") @Min(1) Long bookId,
            @RequestBody @Valid LoanRequestDTO.CheckOutDTO dto
    ) {
        return loanCommandUsecase.executeLoanBookToUser(bookId, dto);
    }

    @PostMapping("/{bookId}/returned/{loanId}")
    public LoanResponseDTO.Returned getReturnedBook(
            @PathVariable(name = "bookId") @Min(1) Long bookId,
            @PathVariable(name = "loanId") @Min(1) Long loanId,
            @RequestBody @Valid LoanRequestDTO.CheckInDTO dto
    ) {
        return loanCommandUsecase.executeReturnBook(loanId, bookId, dto);
    }
}
