package com.rmsoftmissionlkdcode.bookbuddy.module.book.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.service.command.BookCommandUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookCommandApi {
    private final BookCommandUsecase bookCommandUsecase;

    @PostMapping
    public BookResponseDTO.Create getRegister(@RequestBody @Valid BookRequestDTO.Create createDTO) {
        return bookCommandUsecase.executeRegisterBook(createDTO);
    }

    @PatchMapping("/{bookId}")
    public BookResponseDTO.Update getUpdate(
            @PathVariable(name = "bookId") @Min(1) Long bookId,
            @RequestBody @Valid BookRequestDTO.Update updateDTO
    ) {
        return bookCommandUsecase.executeUpdateBook(bookId, updateDTO);
    }
}
