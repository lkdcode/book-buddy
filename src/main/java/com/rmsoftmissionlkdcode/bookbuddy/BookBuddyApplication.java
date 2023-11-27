package com.rmsoftmissionlkdcode.bookbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookBuddyApplication.class, args);
    }

}
