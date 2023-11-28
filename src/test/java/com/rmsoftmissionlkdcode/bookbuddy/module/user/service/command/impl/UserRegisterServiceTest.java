package com.rmsoftmissionlkdcode.bookbuddy.module.user.service.command.impl;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.repository.UserRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.UserEmailDuplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserRegisterServiceTest {
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUserRepository() {
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("유효한 Request 로 회원가입에 성공할 것이다.")
    void shouldSignUpSuccessfullyWithValidRequestTest() {
        // given
        String name = "name";
        String password = "password123";
        String email = "email@test.com";
        UserRequestDTO.SignUp request = UserRequestDTO.SignUp.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();

        UserResponseDTO.SignUp response = userRegisterService.excuteSignUp(request);
        // when
        // then
        assertThat(response.email())
                .isEqualTo(email);
    }

    @Test
    @DisplayName("이메일이 누락된 Request 로 회원가입 진행 시 익셉션이 발생할 것이다.")
    void shouldThrowExceptionWhenSignUpWithMissingEmailTest() {
        // given
        String name = "name";
        String password = "password123";

        UserRequestDTO.SignUp request = UserRequestDTO.SignUp.builder()
                .name(name)
                .password(password)
                .build();
        // when
        // then
        assertThatThrownBy(() -> userRegisterService.excuteSignUp(request))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입 시 익셉션이 발생할 것이다.")
    void shouldThrowExceptionWhenSignUpWithDuplicateEmailTest() {
        // given
        String name = "name";
        String password = "password123";
        String email = "email@test.com";
        UserRequestDTO.SignUp request = UserRequestDTO.SignUp.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();

        // when
        userRepository.save(User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build());

        // then
        assertThatThrownBy(() -> userRegisterService.excuteSignUp(request))
                .isInstanceOf(UserEmailDuplicationException.class);
    }
}