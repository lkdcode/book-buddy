package com.rmsoftmissionlkdcode.bookbuddy.module.user.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.repository.UserRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserCommandApiTest extends BaseApiTest {
    private static final String URL_PREFIX = "/api/users";
    private static final String USER_EMAIL = "test1@test.com";
    private static final String USER_NAME = "홍길동";
    private static final String USER_PASSWORD = "password123";

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setRepository() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입에 성공할 것이다.")
    void shouldSuccessFullySignupUserTest() throws Exception {
        // given
        // when
        // then
        signUp();
    }

    @Test
    @DisplayName("중복 이메일은 회원 가입에 실패할 것이다.")
    void shouldFailFullySignUpWithDuplicateEmail() throws Exception {
        // given
        signUp();

        String expectedErrorMessage = "이메일이 중복되었습니다.";
        String signUpUrl = URL_PREFIX + "/sign-up";

        UserRequestDTO.SignUp request = UserRequestDTO.SignUp.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();

        mockMvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("비밀 번호를 누락할 경우 회원 가입에 실패할 것이다.")
    void shouldFailFullySignUpWithMissingPassword() throws Exception {
        // given
        String signUpUrl = URL_PREFIX + "/sign-up";
        String expectedErrorMessage = "비밀번호를 입력해주세요.";

        UserRequestDTO.SignUp request = UserRequestDTO.SignUp.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .build();

        mockMvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("이메일을 누락할 경우 회원 가입에 실패할 것이다.")
    void shouldFailFullySignUpWithMissingEmail() throws Exception {
        // given
        String signUpUrl = URL_PREFIX + "/sign-up";
        String expectedErrorMessage = "이메일을 입력해주세요.";

        UserRequestDTO.SignUp request = UserRequestDTO.SignUp.builder()
                .name(USER_NAME)
                .password(USER_PASSWORD)
                .build();

        mockMvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    @Test
    @DisplayName("이름을 누락할 경우 회원 가입에 실패할 것이다.")
    void shouldFailFullySignUpWithMissingName() throws Exception {
        // given
        String signUpUrl = URL_PREFIX + "/sign-up";
        String expectedErrorMessage = "이름을 입력해주세요.";

        UserRequestDTO.SignUp request = UserRequestDTO.SignUp.builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();

        mockMvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrorMessage));
    }

    private void signUp() throws Exception {
        String signUpUrl = URL_PREFIX + "/sign-up";
        UserRequestDTO.SignUp request = UserRequestDTO.SignUp.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();

        // when
        // then
        mockMvc.perform(post(signUpUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(USER_EMAIL));

        List<User> userList = userRepository.findAll();
        User user = userList.get(0);

        assertThat(user.getName())
                .isEqualTo(USER_NAME);

        assertThat(user.getEmail())
                .isEqualTo(USER_EMAIL);
    }
}