package com.rmsoftmissionlkdcode.bookbuddy.module.book.dto;

import lombok.Builder;

import static com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO.*;

public sealed interface BookResponseDTO permits Create, Update {
    @Builder
    record Create(
            String ISBN,
            String title,
            String author,
            int quantity
    ) implements BookResponseDTO {
    }

    @Builder
    record Update(
            String ISBN,
            String title,
            String author,
            int quantity
    ) implements BookResponseDTO {
    }
}
