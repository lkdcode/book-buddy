package com.rmsoftmissionlkdcode.bookbuddy.module.book.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO.*;

public sealed interface BookRequestDTO permits Create, Update {
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

    record Update(
            @NotBlank(message = "수정할 ISBN 고유 번호를 입력해주세요.")
            String ISBN,
            @NotBlank(message = "수정할 책의 제목을 입력해주세요.")
            String title,
            @NotBlank(message = "수정할 책의 저자를 입력해주세요.")
            String author,
            @Min(value = 1, message = "수정할 최소 수량은 1권 이상입니다.")
            int quantity
    ) implements BookRequestDTO {
    }
}