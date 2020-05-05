package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.UserRegistrationDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRegistrationMapper {

    UserRegistrationMapper INSTANCE = Mappers.getMapper(UserRegistrationMapper.class);

    AppUser toUser(UserRegistrationDTO dto);

}
