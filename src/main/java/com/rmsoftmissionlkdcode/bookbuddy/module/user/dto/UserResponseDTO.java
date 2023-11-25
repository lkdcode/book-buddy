package com.rmsoftmissionlkdcode.bookbuddy.module.user.dto;

import lombok.Builder;

import static com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO.*;

public sealed interface UserResponseDTO permits SignUp, SignIn {
    @Builder
    record SignUp(
            String email
    ) implements UserResponseDTO {
    }

    @Builder
    record SignIn(
            String jwt
    ) implements UserResponseDTO {
    }
}
