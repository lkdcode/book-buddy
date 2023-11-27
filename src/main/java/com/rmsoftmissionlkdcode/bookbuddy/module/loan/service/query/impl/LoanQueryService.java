package com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.query.impl;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository.LoanRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.mapper.LoanResponseMapper;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.query.LoanQueryUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoanQueryService implements LoanQueryUsecase {
    private final LoanRepository loanRepository;

    @Override
    public List<LoanResponseDTO.History> retrieveFindAllByBookId(Long bookId) {
        List<Loan> loanList = loanRepository.findByBookId(bookId);

        return LoanResponseMapper.INSTANCE.loanToHistoryListDTO(loanList);
    }
}
