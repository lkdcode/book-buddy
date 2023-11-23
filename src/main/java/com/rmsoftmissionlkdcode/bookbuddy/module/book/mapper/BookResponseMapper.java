package com.rmsoftmissionlkdcode.bookbuddy.module.book.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookResponseMapper {
    BookResponseMapper INSTANCE = Mappers.getMapper(BookResponseMapper.class);

    BookResponseDTO.Create bookToCreateDTO(Book book);

    BookResponseDTO.Update bookToUpdateDTO(Book book);
}
