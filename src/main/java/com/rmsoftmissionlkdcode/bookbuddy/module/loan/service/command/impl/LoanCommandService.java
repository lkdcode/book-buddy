package com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command.impl;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository.BookRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.BookNotFoundException;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.enums.BookErrorCode;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository.LoanRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.NotFoundLoanHistoryException;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.exception.enums.LoanErrorCode;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.mapper.LoanResponseMapper;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command.LoanCommandUsecase;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.repository.UserRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.NotFoundUserByEmailException;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.enums.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanCommandService implements LoanCommandUsecase {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LoanResponseDTO.Borrowed executeLoanBookToUser(Long bookId, LoanRequestDTO.CheckOutDTO dto) {
        Book book = findBookById(bookId);
        User user = findUserByEmail(dto.userEmail());

        book.lendBook();

        Loan loan = LoanResponseMapper.INSTANCE.createLoanFromBookAndUser(book, user);

        Loan savedLoan = loanRepository.save(loan);

        return LoanResponseMapper.INSTANCE.loanToBorrowedDTO(savedLoan);
    }

    @Override
    public LoanResponseDTO.Returned executeReturnBook(Long loanId, Long bookId, LoanRequestDTO.CheckInDTO dto) {
        Book book = findBookById(bookId);
        User user = findUserByEmail(dto.userEmail());
        Loan loan = findLoanById(loanId);

        loan.validateReturnUser(user.getEmail());
        loan.validateReturnBookId(book.getId());
        book.returnBook();
        loan.returnedAt();

        return LoanResponseMapper.INSTANCE.loanToReturnedDTO(loan);
    }

    private Loan findLoanById(Long loanId) {
        return loanRepository.findById(loanId).orElseThrow(
                () -> new NotFoundLoanHistoryException(LoanErrorCode.NON_EXISTENT_LOAN_ID_ERROR));
    }

    private Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(BookErrorCode.NOT_FOUND_BOOK_BY_ID_ERROR));
    }

    private User findUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(
                () -> new NotFoundUserByEmailException(UserErrorCode.NOT_FOUND_USER_BY_EMAIL_ERROR));
    }
}
