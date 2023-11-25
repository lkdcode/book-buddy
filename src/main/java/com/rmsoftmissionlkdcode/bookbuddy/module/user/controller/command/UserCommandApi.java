package com.rmsoftmissionlkdcode.bookbuddy.module.user.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.service.command.UserRegisterUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCommandApi {
    private final UserRegisterUsecase userRegisterUsecase;

    @PostMapping("/sign-up")
    public UserResponseDTO.SignUp getSignup(
            @RequestBody @Valid UserRequestDTO.SignUp dto
    ) {
        return userRegisterUsecase.excuteSignUp(dto);
    }
}
