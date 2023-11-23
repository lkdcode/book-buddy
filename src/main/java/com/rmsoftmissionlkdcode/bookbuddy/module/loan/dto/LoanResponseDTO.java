package com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto;

import static com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO.Loan;
import static com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO.Returned;

public sealed interface LoanResponseDTO permits Loan, Returned {
    record Loan(
            boolean success
    ) implements LoanResponseDTO {
    }

    record Returned(
            boolean success
    ) implements LoanResponseDTO {
    }
}
