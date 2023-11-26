package com.rmsoftmissionlkdcode.bookbuddy.module.loan.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoanResponseMapper {
    LoanResponseMapper INSTANCE = Mappers.getMapper(LoanResponseMapper.class);

    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "title", source = "book.title")
    @Mapping(target = "author", source = "book.author")
    LoanResponseDTO.Borrowed loanToBorrowedDTO(Loan loan);

    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "title", source = "book.title")
    @Mapping(target = "author", source = "book.author")
    LoanResponseDTO.Returned loanToReturnedDTO(Loan loan);

    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "title", source = "book.title")
    @Mapping(target = "author", source = "book.author")
    LoanResponseDTO.History loanToHistoryDTO(Loan loan);

    Loan createLoanFromBookAndUser(Book book, User user);

    List<LoanResponseDTO.History> loanToHistoryListDTO(List<Loan> loan);
}
