package com.rmsoftmissionlkdcode.bookbuddy.module.user.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserResponseMapperTest {
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "password123";
    private static final String NAME = "홍길동";
    private User user;

    @BeforeEach
    void setUser() {
        this.user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
    }

    @Test
    @DisplayName("User Entity를 SignUpDTO로 변환에 성공할 것이다.")
    void convert_User_To_SignUp_DTO_Success() {
        // given
        // when
        UserResponseDTO.SignUp signUpDTO = UserResponseMapper.INSTANCE.userToSignUpDTO(user);
        // then
        assertThat(signUpDTO.email())
                .isEqualTo(EMAIL);
    }

}