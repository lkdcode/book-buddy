package com.rmsoftmissionlkdcode.bookbuddy.module.book.dto;

import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO.Create;
import static com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO.Update;

public sealed interface BookRequestDTO permits Create, Update {
    @Builder
    record Create(
            @NotBlank(message = "ISBN 고유 번호를 입력해주세요.")
            String ISBN,
            @NotBlank(message = "책의 제목을 입력해주세요.")
            String title,
            @NotBlank(message = "책의 저자를 입력해주세요.")
            String author,
            @Min(value = 1, message = "최소 수량은 1권 이상입니다.")
            int quantity
    ) implements BookRequestDTO {
    }

    @Builder
    record Update(
            String ISBN,
            String title,
            String author,
            //TODO : Long 으로 null 허용
            Long quantity
    ) implements BookRequestDTO {
    }
}
