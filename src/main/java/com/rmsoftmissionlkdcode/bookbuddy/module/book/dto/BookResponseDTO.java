package com.rmsoftmissionlkdcode.bookbuddy.module.book.dto;

import static com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO.*;

public sealed interface BookResponseDTO permits Create, Update {
    record Create(
            boolean success
    ) implements BookResponseDTO {
    }

    record Update(
            boolean success
    ) implements BookResponseDTO {
    }
}
