package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.UserDetailsDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDetailsMapper {

    UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    UserDetailsDTO toDTO(AppUser appUser);

}
