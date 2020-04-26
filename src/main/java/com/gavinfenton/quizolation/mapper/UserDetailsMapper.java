package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.UserDetailsDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDetailsMapper {

    UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    UserDetailsDTO toUserDetailsDTO(AppUser appUser);

}
