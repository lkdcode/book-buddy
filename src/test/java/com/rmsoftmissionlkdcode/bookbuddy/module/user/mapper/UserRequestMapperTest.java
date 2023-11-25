package com.rmsoftmissionlkdcode.bookbuddy.module.user.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseRepositoryList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestMapperTest extends BaseRepositoryList {

    @Test
    @DisplayName("유효한 signUpDTO를 User Entity로 변환에 성공할 것이다.")
    void convertValidSignUpDTOToUserSuccessTest() {
        // given
        UserRequestDTO.SignUp validSignUpDTO = UserRequestDTO.SignUp.builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .name(USER_NAME)
                .build();

        // when
        User user = UserRequestMapper.INSTANCE.signUpDTOToUser(validSignUpDTO);

        // then
        assertThat(user.getEmail())
                .isEqualTo(USER_EMAIL);

        assertThat(user.getPassword())
                .isEqualTo(USER_PASSWORD);

        assertThat(user.getName())
                .isEqualTo(USER_NAME);
    }

    @Test
    @DisplayName("유효한 signUpDTO를 User Entity로 변환에 성공할 것이다.")
    void convertInvalidSignUpDTOToUserSuccessTest() {
        // given
        UserRequestDTO.SignUp invalidSignUpDTO = UserRequestDTO.SignUp.builder()
                .build();

        // when
        User user = UserRequestMapper.INSTANCE.signUpDTOToUser(invalidSignUpDTO);

        // then
        assertThat(user.getEmail())
                .isNull();

        assertThat(user.getPassword())
                .isNull();

        assertThat(user.getName())
                .isNull();
    }
}