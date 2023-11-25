package com.rmsoftmissionlkdcode.bookbuddy.module.user.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.support.base.BaseRepositoryList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseMapperTest extends BaseRepositoryList {

    @Test
    @DisplayName("유효한 User Entity를 SignUpDTO로 변환에 성공할 것이다.")
    void convertValidUserToSignUpDTOSuccessTest() {
        // given
        User validUser = super.user;

        // when
        UserResponseDTO.SignUp signUpDTO = UserResponseMapper.INSTANCE.userToSignUpDTO(validUser);

        // then
        assertThat(signUpDTO.email())
                .isEqualTo(USER_EMAIL);
    }

    @Test
    @DisplayName("유효하지 않은 User Entity를 SignUpDTO로 변환에 성공할 것이다.")
    void convertInvalidUserToSignUpDTOSuccessTest() {
        // given
        User invalidUser = User.builder()
                .build();

        // when
        UserResponseDTO.SignUp signUpDTO = UserResponseMapper.INSTANCE.userToSignUpDTO(invalidUser);

        // then
        assertThat(signUpDTO.email())
                .isNull();
    }
}