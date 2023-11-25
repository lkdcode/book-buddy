package com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO.Borrowed;
import static com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO.Returned;

public sealed interface LoanResponseDTO permits Borrowed, Returned {
    @Builder
    record Borrowed(
            Long loanId,
            String userEmail,
            String title,
            String author,
            @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
            LocalDateTime borrowedAt
    ) implements LoanResponseDTO {
    }

    @Builder
    record Returned(
            String userEmail,
            String title,
            String author,
            @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
            LocalDateTime borrowedAt,
            @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
            LocalDateTime returnedAt
    ) implements LoanResponseDTO {
    }
}
