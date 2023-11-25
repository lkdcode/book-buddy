package com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO.CheckOutDTO;
import static com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO.CheckInDTO;

public sealed interface LoanRequestDTO permits CheckOutDTO, CheckInDTO {
    record CheckOutDTO(
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String userEmail
    ) implements LoanRequestDTO {
    }

    record CheckInDTO(
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String userEmail
    ) implements LoanRequestDTO {
    }
}
