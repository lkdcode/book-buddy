package com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.query;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;

import java.util.List;

public interface LoanQueryUsecase {
    List<LoanResponseDTO.History> retrieveFindAllByBookId(Long bookId);
}
