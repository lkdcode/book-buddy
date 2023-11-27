package com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l " +
            "from Loan l " +
            "where l.id = :id")
    Optional<Loan> findByIdForUpdate(@Param("id") Long loanId);

    List<Loan> findByBookId(Long bookId);

    boolean existsById(Long id);
}
