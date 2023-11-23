package com.rmsoftmissionlkdcode.bookbuddy.module.loan.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanResponseMapper {
    LoanResponseMapper INSTANCE = Mappers.getMapper(LoanResponseMapper.class);

    LoanResponseDTO.Loan loanToDTO(Loan loan);

    Loan createLoanFromBookAndUser(Book book, User user);
}
