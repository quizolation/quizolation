package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.TeamDTO;
import com.gavinfenton.quizolation.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    TeamDTO toDTO(Team team);

    Team toTeam(TeamDTO dto);

    List<TeamDTO> toDTOList(List<Team> team);

}
