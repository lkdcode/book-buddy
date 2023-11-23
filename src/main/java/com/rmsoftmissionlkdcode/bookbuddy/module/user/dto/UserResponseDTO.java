package com.rmsoftmissionlkdcode.bookbuddy.module.user.dto;

import static com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO.*;

public sealed interface UserResponseDTO permits SignUp, SignIn {
    record SignUp(
            String email
    ) implements UserResponseDTO {
    }

    record SignIn(
            String jwt
    ) implements UserResponseDTO {
    }
}
