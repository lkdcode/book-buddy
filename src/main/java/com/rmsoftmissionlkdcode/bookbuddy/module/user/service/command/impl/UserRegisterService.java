package com.rmsoftmissionlkdcode.bookbuddy.module.user.service.command.impl;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.repository.UserRepository;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserResponseDTO;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.UserEmailDuplicationException;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.exception.enums.UserErrorCode;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.mapper.UserRequestMapper;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.mapper.UserResponseMapper;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.service.command.UserRegisterUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRegisterService implements UserRegisterUsecase {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO.SignUp excuteSignUp(UserRequestDTO.SignUp signUpDTO) {
        checkDuplicateEmail(signUpDTO.email());

        User user = UserRequestMapper.INSTANCE.signUpDTOToUser(signUpDTO);
        User savedUser = userRepository.save(user);

        return UserResponseMapper.INSTANCE.userToSignUpDTO(savedUser);
    }

    private void checkDuplicateEmail(String email) {
        boolean existsByEmail = userRepository.existsByEmail(email);

        if (existsByEmail) {
            throw new UserEmailDuplicationException(UserErrorCode.USER_EMAIL_DUPLICATION_ERROR);
        }
    }
}
