package com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByBookId(Long bookId);

    boolean existsById(Long id);
}
