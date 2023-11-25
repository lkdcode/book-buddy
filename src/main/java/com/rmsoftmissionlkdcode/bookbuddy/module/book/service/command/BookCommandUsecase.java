package com.rmsoftmissionlkdcode.bookbuddy.module.book.service.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO;

public interface BookCommandUsecase {
    BookResponseDTO.Create executeRegisterBook(BookRequestDTO.Create createDTO);

    BookResponseDTO.Update executeUpdateBook(Long bookId, BookRequestDTO.Update updateDTO);
}
