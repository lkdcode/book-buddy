package com.rmsoftmissionlkdcode.bookbuddy.module.loan.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command.LoanCommandUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Tag(name = "loan", description = "도서 대출 API")
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanCommandApi {
    private final LoanCommandUsecase loanCommandUsecase;

    @Operation(summary = "도서 대출 Api", description = "도서를 대출합니다.", tags = "loan", responses = {
            @ApiResponse(responseCode = "200", description = "도서 대출에 성공하였습니다.")
            , @ApiResponse(responseCode = "400", description = "도서 대출의 요청 값이 잘 못 되었습니다.")
            , @ApiResponse(responseCode = "404", description = "존재하지 않는 도서입니다.<br>존재하지 않는 회원입니다.<br>도서 수량이 부족합니다.")
    })
    @PostMapping("/{bookId}")
    public LoanResponseDTO.Borrowed getLoanBookToUser(
            @PathVariable(name = "bookId") @Min(1) Long bookId,
            @RequestBody @Valid LoanRequestDTO.CheckOutDTO dto
    ) {
        return loanCommandUsecase.executeLoanBookToUser(bookId, dto);
    }

    @Operation(summary = "도서 반납 Api", description = "대출한 도서를 반납합니다.", tags = "loan", responses = {
            @ApiResponse(responseCode = "200", description = "도서 반납에 성공하였습니다.")
            , @ApiResponse(responseCode = "400", description = "대출한 도서 반납의 요청 값이 잘 못 되었습니다.")
            , @ApiResponse(responseCode = "404", description = "존재하지 않는 도서입니다.<br>존재하지 않는 회원입니다.<br>존재하지 않는 대출 내역입니다.")
    })
    @PostMapping("/{bookId}/returned/{loanId}")
    public LoanResponseDTO.Returned getReturnedBook(
            @PathVariable(name = "bookId") @Min(1) Long bookId,
            @PathVariable(name = "loanId") @Min(1) Long loanId,
            @RequestBody @Valid LoanRequestDTO.CheckInDTO dto
    ) {
        return loanCommandUsecase.executeReturnBook(loanId, bookId, dto);
    }
}
