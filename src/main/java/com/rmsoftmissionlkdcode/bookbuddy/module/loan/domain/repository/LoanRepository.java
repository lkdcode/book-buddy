package com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository;

import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsById(Long id);
}
