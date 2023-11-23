package com.rmsoftmissionlkdcode.bookbuddy.module.user.service.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO;

public interface UserRegisterUsecase {
    UserResponseDTO.SignUp excuteSignUp(UserRequestDTO.SignUp signUpDTO);
}
