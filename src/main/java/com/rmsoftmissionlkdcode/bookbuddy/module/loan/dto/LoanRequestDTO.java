package com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO.Loan;
import static com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO.Returned;

public sealed interface LoanRequestDTO permits Loan, Returned {
    record Loan(
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String userEmail
    ) implements LoanRequestDTO {
    }

    record Returned(
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String userEmail
    ) implements LoanRequestDTO {
    }
}
