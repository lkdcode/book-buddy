package com.rmsoftmissionlkdcode.bookbuddy.module.user.mapper;

import com.rmsoftmissionlkdcode.bookbuddy.module.user.domain.User;
import com.rmsoftmissionlkdcode.bookbuddy.module.user.dto.UserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRequestMapper {
    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

    User signUpDTOToUser(UserRequestDTO.SignUp signUpDTO);
}
