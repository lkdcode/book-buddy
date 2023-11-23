package com.rmsoftmissionlkdcode.bookbuddy.module.user.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestMapperTest {
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "password123";
    private static final String NAME = "홍길동";

    @Test
    @DisplayName("signUpDTO를 User Entity로 변환에 성공할 것이다.")
    void convert_sign_up_DTO_To_User_Success() {
        // given
        UserRequestDTO.SignUp signUpDTO = new UserRequestDTO.SignUp(EMAIL, PASSWORD, NAME);

        // when
        User user = UserRequestMapper.INSTANCE.signUpDTOToUser(signUpDTO);

        // then
        assertThat(user.getEmail())
                .isEqualTo(EMAIL);

        assertThat(user.getPassword())
                .isEqualTo(PASSWORD);

        assertThat(user.getName())
                .isEqualTo(NAME);
    }

}