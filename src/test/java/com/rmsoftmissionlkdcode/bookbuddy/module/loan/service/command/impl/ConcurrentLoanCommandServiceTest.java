package com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command.impl;

import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.Book;
import com.rmsoftmissionlkdcode.bookbuddy.module.book.domain.repository.BookRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.Loan;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.domain.repository.LoanRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.dto.LoanRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.loan.service.command.LoanCommandUsecase;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml", properties = "spring.config.name=application-test")
class ConcurrentLoanCommandServiceTest {
    @Autowired
    private LoanCommandUsecase loanCommandUsecase;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LoanRepository loanRepository;

    private Book book;
    private User user;
    private Loan loan;
    private Long bookId;
    private Long bookQuantity;
    private String userEmail;

    @BeforeEach
    void set() {
        userRepository.deleteAll();
        bookRepository.deleteAll();
        loanRepository.deleteAll();

        this.user = userRepository.save(User.builder()
                .email("email@test.com")
                .name("name")
                .password("password123")
                .build());

        this.book = bookRepository.save(Book.builder()
                .ISBN("1234561234560")
                .title("title")
                .author("author")
                .quantity(3L)
                .build());

        this.loan = loanRepository.save(Loan.builder()
                .user(user)
                .book(book)
                .build());

        this.bookId = book.getId();
        this.bookQuantity = book.getQuantity();
        this.userEmail = user.getEmail();
    }

    @Test
    @DisplayName("대출 가능한 도서 수보다 많은 사용자가 동시에 대출을 시도하더라도 대출 가능한 도서 수만큼만 성공할 것이다.")
    void shouldSuccessFullyHandleConcurrentLoanLimitedBooksTest() throws Exception {
        // given
        int userCount = 50;

        LoanRequestDTO.CheckOutDTO request = LoanRequestDTO.CheckOutDTO.builder()
                .userEmail(userEmail)
                .build();

        ExecutorService executorService = Executors.newFixedThreadPool(userCount);
        CountDownLatch latch = new CountDownLatch(userCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // when
        for (int i = 0; i < userCount; i++) {
            executorService.submit(() -> {
                try {
                    loanCommandUsecase.executeLoanBookToUser(bookId, request);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        assertThat((long) successCount.get())
                .isEqualTo(bookQuantity);

        assertThat(failCount.get())
                .isEqualTo(userCount - bookQuantity);
    }

    @Test
    @DisplayName("반납 가능한 도서 수보다 동시에 많은 요청이 오더라도 반납 가능한 도서 수만큼만 반납에 성공할 것이다.")
    void shouldSuccessFullyLimitedBooksOnConcurrentRequests() throws Exception {
        // given
        this.loan = loanRepository.save(Loan.builder()
                .user(user)
                .book(book)
                .build());

        int returnRequestCount = 30;
        int returnableCount = 1;

        LoanRequestDTO.CheckInDTO request = LoanRequestDTO.CheckInDTO.builder()
                .userEmail(user.getEmail())
                .build();

        ExecutorService executorService = Executors.newFixedThreadPool(returnRequestCount);
        CountDownLatch latch = new CountDownLatch(returnRequestCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < returnRequestCount; i++) {
            executorService.submit(() -> {
                try {
                    loanCommandUsecase.executeReturnBook(loan.getId(), bookId, request);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // when
        // then
        assertThat(successCount.get())
                .isEqualTo(returnableCount);

        assertThat(failCount.get())
                .isEqualTo(returnRequestCount - returnableCount);
    }
}