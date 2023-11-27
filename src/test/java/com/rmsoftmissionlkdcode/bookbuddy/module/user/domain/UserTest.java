package com.rmsoftmissionlkdcode.bookbuddy.module.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final String USER_EMAIL = "test1@naver.com";
    private static final String USER_NAME = "홍길동";
    private static final String USER_PASSWORD = "password123";

    @Test
    @DisplayName("User 생성에 성공할 것이다.")
    void createUserEntitySuccessTest() {
        // given
        // when
        User user = User.builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .name(USER_NAME)
                .build();

        // then
        assertThat(user.getEmail())
                .isEqualTo(USER_EMAIL);

        assertThat(user.getName())
                .isEqualTo(USER_NAME);

        assertThat(user.getPassword())
                .isEqualTo(USER_PASSWORD);
    }
}