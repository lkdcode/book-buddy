package com.rmsoftmissionlkdcode.bookbuddy.module.loan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanRequestMapper {
    LoanRequestMapper INSTANCE = Mappers.getMapper(LoanRequestMapper.class);
}
