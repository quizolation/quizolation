package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.repository.TeamRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateTeam() {
        Team teamSaving = new Team();
        teamSaving.setName("Team name");
        Team teamExpected = new Team();

        given(teamRepository.save(teamSaving)).willReturn(teamExpected);

        Team teamActual = teamService.createTeam(teamSaving);

        verify(teamRepository).save(teamSaving);
        assertEquals(teamExpected, teamActual);
    }

    @Test
    public void testUpdateTeam() {
        Team teamSaving = new Team();
        teamSaving.setId(1L);
        teamSaving.setName("Team name");
        Long idExpected = teamSaving.getId();
        Team teamExpected = new Team();

        given(teamRepository.save(teamSaving)).willReturn(teamExpected);

        Team teamActual = teamService.updateTeam(idExpected, teamSaving);
        assertEquals(teamActual, teamExpected);
    }

    @Test
    public void testGetTeam() {
        Team teamSaving = new Team();
        teamSaving.setId(1L);
        teamSaving.setName("Team name");
        Team teamExpected = new Team();
        Long idExpected = teamSaving.getId();

        given(teamRepository.findById(idExpected)).willReturn(Optional.of(teamExpected));

        Team teamActual = teamService.getTeam(idExpected);

        verify(teamRepository).findById(idExpected);
        assertEquals(teamActual, teamExpected);
    }

    @Test
    public void testGetTeamThrowsException() {
        Long idExpected = 32L;
        given(teamRepository.findById(idExpected)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> teamService.getTeam(idExpected));
    }

    @Test
    public void testGetTeamsReturnsExistingTeamsFromRepo() {
        Team team1 = new Team();
        Team team2 = new Team();
        team1.setName("Team 1");
        team2.setName("Team 2");
        List<Team> teamsExpected = Arrays.asList(team1, team2);
        given(teamRepository.findAll()).willReturn(teamsExpected);

        //When
        List<Team> teamsActual = teamService.getTeams();

        //Then
        verify(teamRepository).findAll();
        assertEquals(teamsExpected, teamsActual);
    }

    @Test
    public void testDeleteTeam(){
        // Given
        Long idDeleting = 321L;

        // When
        teamService.deleteTeam(idDeleting);

        // Then
        verify(teamRepository).deleteById(idDeleting);

    }

}
