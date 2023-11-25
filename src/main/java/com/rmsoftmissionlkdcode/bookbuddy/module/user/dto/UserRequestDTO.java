package com.rmsoftmissionlkdcode.bookbuddy.module.user.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO.*;

public sealed interface UserRequestDTO permits SignUp {
    @Builder
    record SignUp(
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password,
            @NotBlank(message = "이름을 입력해주세요.")
            String name
    ) implements UserRequestDTO {
    }
}
