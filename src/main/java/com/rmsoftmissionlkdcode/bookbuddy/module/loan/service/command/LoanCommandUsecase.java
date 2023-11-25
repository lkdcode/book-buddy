package com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;

public interface LoanCommandUsecase {
    LoanResponseDTO.Loan executeLoanBookToUser(Long bookId, LoanRequestDTO.Loan dto);

    LoanResponseDTO.Returned executeReturnBook(Long loanId, Long bookId, LoanRequestDTO.Returned dto);
}
