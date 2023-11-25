package com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByISBN(String ISBN);
}
