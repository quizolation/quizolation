package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.RoundDTO;
import com.gavinfenton.quizolation.entity.Round;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoundMapper {

    RoundMapper INSTANCE = Mappers.getMapper(RoundMapper.class);

    RoundDTO toDTO(Round round);

    Round toRound(RoundDTO dto);

    List<RoundDTO> toDTOList(List<Round> roundList);

}
