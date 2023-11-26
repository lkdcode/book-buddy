package com.rmsoftmissionlkdcode.bookbuddy.module.book.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.service.command.BookCommandUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Tag(name = "book", description = "도서 API")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookCommandApi {
    private final BookCommandUsecase bookCommandUsecase;

    @Operation(summary = "도서 등록 Api", description = "신규 도서를 등록합니다.", tags = "book", responses = {
            @ApiResponse(responseCode = "200", description = "도서 등록에 성공하였습니다.")
            , @ApiResponse(responseCode = "400", description = "도서 등록의 요청 값이 잘 못 되었습니다.")
            , @ApiResponse(responseCode = "409", description = "이미 등록되어 있는 도서입니다.")
    })
    @PostMapping
    public BookResponseDTO.Create getRegister(
            @RequestBody @Valid BookRequestDTO.Create createDTO
    ) {
        return bookCommandUsecase.executeRegisterBook(createDTO);
    }

    @Operation(summary = "도서 수정 Api", description = "등록된 도서의 정보를 수정합니다.", tags = "book", responses = {
            @ApiResponse(responseCode = "200", description = "도서 수정에 성공하였습니다.")
            , @ApiResponse(responseCode = "400", description = "도서 수정의 요청 값이 잘 못 되었습니다.")
            , @ApiResponse(responseCode = "404", description = "존재하지 않는 도서입니다.")
    })
    @PatchMapping("/{bookId}")
    public BookResponseDTO.Update getUpdate(
            @PathVariable(name = "bookId") @Min(1) Long bookId,
            @RequestBody @Valid BookRequestDTO.Update updateDTO
    ) {
        return bookCommandUsecase.executeUpdateBook(bookId, updateDTO);
    }
}
