package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.TeamDTO;
import com.gavinfenton.quizolation.entity.Team;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TeamMapperTest {

    TeamMapper teamMapper = TeamMapper.INSTANCE;

    @Test
    public void testToDTOMapsToAllDTOFields() {
        // Given
        Team team = new Team();
        team.setId(123L);
        team.setName("A Team");

        // When
        TeamDTO dto = teamMapper.toDTO(team);

        // Then
        assertEquals(team.getId(), dto.getId());
        assertEquals(team.getName(), dto.getName());
    }

    @Test
    public void testToTeamMapsFromAllDTOFields() {
        // Given
        TeamDTO dto = new TeamDTO();
        dto.setId(123L);
        dto.setName("A Team");

        // When
        Team team = teamMapper.toTeam(dto);

        // Then
        assertEquals(dto.getId(), team.getId());
        assertNull(team.getLeaderId());
        assertEquals(dto.getName(), team.getName());
    }

    @Test
    public void testToDTOListMapsToAllDTOFields() {
        // Given
        Team team = new Team();
        team.setId(123L);
        team.setName("A Team");
        List<Team> teamList = Collections.singletonList(team);

        // When
        List<TeamDTO> dtoList = teamMapper.toDTOList(teamList);
        TeamDTO dto = dtoList.get(0);

        // Then
        assertEquals(1, dtoList.size());
        assertEquals(team.getId(), dto.getId());
        assertEquals(team.getName(), dto.getName());
    }

}
