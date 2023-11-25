package com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;

public interface LoanCommandUsecase {
    LoanResponseDTO.Borrowed executeLoanBookToUser(Long bookId, LoanRequestDTO.CheckOutDTO dto);

    LoanResponseDTO.Returned executeReturnBook(Long loanId, Long bookId, LoanRequestDTO.CheckInDTO dto);
}
