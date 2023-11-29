package com.rmsoftmissionlkdcode.bookbuddy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml", properties = "spring.config.name=application-test")
class BookBuddyApplicationTests {

	@Test
	void contextLoads() {
	}

}
