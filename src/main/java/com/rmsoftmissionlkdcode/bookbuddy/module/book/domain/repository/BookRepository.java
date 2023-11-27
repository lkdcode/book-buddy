package com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b " +
            "from Book b " +
            "where b.id = :id")
    Optional<Book> findByIdForUpdate(@Param("id") Long id);

    Book findByISBN(String ISBN);

    boolean existsByISBN(String ISBN);
}
