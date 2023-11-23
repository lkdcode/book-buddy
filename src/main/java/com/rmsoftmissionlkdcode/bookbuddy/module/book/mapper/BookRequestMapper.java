package com.rmsoftmissionlkdcode.bookbuddy.module.book.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.dto.BookRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookRequestMapper {
    BookRequestMapper INSTANCE = Mappers.getMapper(BookRequestMapper.class);

    Book createDTOToBook(BookRequestDTO.Create createDTO);
}
