package com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.repository;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY
        , connection = EmbeddedDatabaseConnection.H2)
@Sql("/bulk/user-bulk-data.sql")
@TestPropertySource(locations = "classpath:application-test.yml", properties = "spring.config.name=application-test")
class UserRepositoryTest {
    private static final Long ID = 7L;
    private static final String NAME = "조길동";
    private static final String VALID_EMAIL = "test7@test.com";
    private static final String INVALID_EMAIL = "test99@test.com";
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입한 유저는 이메일 조회에 성공할 것이다.")
    void shouldRetrieveRegisteredUser() {
        // given
        // when
        Optional<User> userOptional = userRepository.findByEmail(VALID_EMAIL);

        // then
        assertThat(userOptional.isPresent())
                .isTrue();

        User user = userOptional.get();

        assertThat(user.getId())
                .isEqualTo(ID);

        assertThat(user.getEmail())
                .isEqualTo(VALID_EMAIL);

        assertThat(user.getName())
                .isEqualTo(NAME);
    }

    @Test
    @DisplayName("회원가입하지 않은 유저는 이메일 조회에 실패할 것이다.")
    void shouldReturnEmptyOptionalForNonExistingUserEmail() {
        // given
        // when
        Optional<User> user = userRepository.findByEmail(INVALID_EMAIL);

        // then
        assertThat(user.isPresent())
                .isFalse();

        assertThatThrownBy(user::get)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("회원가입한 이메일은 True 리턴에 성공할 것이다.")
    void shouldReturnTrueForValidEmail() {
        // given
        // when
        boolean existsByEmail = userRepository.existsByEmail(VALID_EMAIL);

        // then
        assertThat(existsByEmail)
                .isTrue();
    }

    @Test
    @DisplayName("회원가입하지 않은 이메일은 False 리턴에 성공할 것이다.")
    void shouldReturnFalseForInvalidEmail() {
        // given
        boolean existsByEmail = userRepository.existsByEmail(INVALID_EMAIL);

        // then
        assertThat(existsByEmail)
                .isFalse();
    }
}