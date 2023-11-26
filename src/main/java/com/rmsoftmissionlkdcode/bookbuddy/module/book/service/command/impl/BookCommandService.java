package com.rmsoftmissionlkdcode.bookbuddy.module.book.service.command.impl;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository.BookRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.BookAlreadyExistsException;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.BookNotFoundException;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.NoChangesToApplyException;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.exception.enums.BookErrorCode;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.mapper.BookRequestMapper;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.mapper.BookResponseMapper;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.service.command.BookCommandUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCommandService implements BookCommandUsecase {
    private final BookRepository bookRepository;

    @Override
    public BookResponseDTO.Create executeRegisterBook(BookRequestDTO.Create createDTO) {
        checkForDuplicateISBN(createDTO.ISBN());

        Book book = BookRequestMapper.INSTANCE.createDTOToBook(createDTO);

        Book saved = bookRepository.save(book);
        return BookResponseMapper.INSTANCE.bookToCreateDTO(saved);
    }

    @Override
    public BookResponseDTO.Update executeUpdateBook(Long bookId, BookRequestDTO.Update updateDTO) {
        Book book = findById(bookId);

        if (!checkForChange(book, updateDTO)) {
            throw new NoChangesToApplyException(BookErrorCode.NO_CHANGE_BOOK_INFORMATION);
        }

        if (isISBNChanged(book, updateDTO)) {
            book.updateISBN(updateDTO.ISBN());
        }

        if (isTitleChanged(book, updateDTO)) {
            book.updateTitle(updateDTO.title());
        }

        if (isAuthorChanged(book, updateDTO)) {
            book.updateAuthor(updateDTO.author());
        }

        if (isQuantityChanged(book, updateDTO)) {
            book.updateQuantity(updateDTO.quantity());
        }

        Book updated = bookRepository.save(book);

        return BookResponseMapper.INSTANCE.bookToUpdateDTO(updated);
    }

    private Book findById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(BookErrorCode.NOT_FOUND_BOOK_BY_ID_ERROR)
        );
    }

    private void checkForDuplicateISBN(String isbn) {
        boolean existsByIsbn = bookRepository.existsByISBN(isbn);

        if (existsByIsbn) {
            throw new BookAlreadyExistsException(BookErrorCode.BOOK_ISBN_DUPLICATION_ERROR);
        }
    }

    private boolean checkForChange(Book book, BookRequestDTO.Update updateDTO) {
        if (isISBNChanged(book, updateDTO)) return true;

        if (isTitleChanged(book, updateDTO)) return true;

        if (isAuthorChanged(book, updateDTO)) return true;

        return isQuantityChanged(book, updateDTO);
    }

    private static boolean isISBNChanged(Book book, BookRequestDTO.Update updateDTO) {
        return !book.getISBN().equals(updateDTO.ISBN());
    }

    private static boolean isTitleChanged(Book book, BookRequestDTO.Update updateDTO) {
        return !book.getTitle().equals(updateDTO.title());
    }

    private static boolean isAuthorChanged(Book book, BookRequestDTO.Update updateDTO) {
        return !book.getAuthor().equals(updateDTO.author());
    }

    private static boolean isQuantityChanged(Book book, BookRequestDTO.Update updateDTO) {
        return book.getQuantity() != updateDTO.quantity();
    }
}
