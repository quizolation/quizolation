package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.UserLoginDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserLoginMapper {

    UserLoginMapper INSTANCE = Mappers.getMapper(UserLoginMapper.class);

    AppUser toUser(UserLoginDTO dto);

}
