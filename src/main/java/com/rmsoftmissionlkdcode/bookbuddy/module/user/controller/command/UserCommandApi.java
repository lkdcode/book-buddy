package com.rmsoftmissionlkdcode.bookbuddy.module.user.controller.command;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.service.command.UserRegisterUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "user", description = "사용자 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCommandApi {
    private final UserRegisterUsecase userRegisterUsecase;

    @Operation(summary = "회원 가입 Api", description = "신규 회원을 등록합니다.", tags = "user", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입에 성공하였습니다.")
            , @ApiResponse(responseCode = "400", description = "회원가입의 요청 값이 잘 못 되었습니다.")
            , @ApiResponse(responseCode = "409", description = "이메일이 중복되었습니다.")
    })
    @PostMapping("/sign-up")
    public UserResponseDTO.SignUp getSignup(
            @RequestBody @Valid UserRequestDTO.SignUp dto
    ) {
        return userRegisterUsecase.excuteSignUp(dto);
    }
}
