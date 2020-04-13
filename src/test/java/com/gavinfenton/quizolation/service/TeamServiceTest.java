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
    public void testCreateTeamSavesToAndReturnsFromRepository() {
        //Given
        Team teamSaving = new Team();
        teamSaving.setName("Team name");
        Team teamExpected = new Team();
        given(teamRepository.save(teamSaving)).willReturn(teamExpected);

        //When
        Team teamActual = teamService.createTeam(teamSaving);

        //When
        verify(teamRepository).save(teamSaving);
        assertEquals(teamExpected, teamActual);
    }

    @Test
    public void testCreateTeamNullsIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Team teamSaving = new Team();
        teamSaving.setId(123L);
        teamSaving.setName("Some Quiz");
        Team teamExpected = new Team();
        given(teamRepository.save(teamSaving)).willReturn(teamExpected);

        // When
        Team teamActual = teamService.createTeam(teamSaving);

        // Then
        assertNull(teamSaving.getId());
        verify(teamRepository).save(teamSaving);
        assertEquals(teamExpected, teamActual);
    }

    @Test
    public void testUpdateTeamSavesToAndReturnsFromRepository() {
        //Given
        Team teamSaving = new Team();
        teamSaving.setId(1L);
        teamSaving.setName("Team name");
        Long idExpected = teamSaving.getId();
        Team teamExpected = new Team();
        given(teamRepository.save(teamSaving)).willReturn(teamExpected);

        //When
        Team teamActual = teamService.updateTeam(idExpected, teamSaving);

        //Then
        verify(teamRepository).save(teamSaving);
        assertEquals(teamActual, teamExpected);
    }

    @Test
    public void testGetTeamReturnsExistingTeamFromRepository() {
        // Given
        Team teamExpected1 = new Team();
        Team teamExpected2 = new Team();
        teamExpected1.setName("Team 1");
        teamExpected2.setName("Team 2");
        List<Team> teamsExpected = Arrays.asList(teamExpected1, teamExpected2);
        given(teamRepository.findAll()).willReturn(teamsExpected);

        //When
        List<Team> teamsActual = teamService.getTeams();

        //Then
        verify(teamRepository).findAll();
        assertEquals(teamsActual, teamsExpected);
    }

    @Test
    public void testGetTeamThrowsExceptionForNonExistingTeam() {
        //Given
        Long idExpected = 32L;
        given(teamRepository.findById(idExpected)).willReturn(Optional.empty());

        //When/then
        assertThrows(ObjectNotFoundException.class, () -> teamService.getTeam(idExpected));
    }

    @Test
    public void testGetTeamsReturnsExistingTeamsFromRepo() {
        //Given
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
    public void testDeleteTeamCallsDeleteOnRepository() {
        // Given
        Long idDeleting = 321L;

        // When
        teamService.deleteTeam(idDeleting);

        // Then
        verify(teamRepository).deleteById(idDeleting);
    }

    @Test
    public void testUpdateTeamSetsIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Team teamSaving = new Team();
        teamSaving.setName("Some Team");
        teamSaving.setId(32L);
        Team teamExpected = new Team();
        Long idExpected = 1L;
        teamExpected.setId(idExpected);
        given(teamRepository.save(teamSaving)).willReturn(teamExpected);

        // When
        Team teamActual = teamService.updateTeam(idExpected, teamSaving);

        // Then
        assertEquals(idExpected, teamSaving.getId());
        verify(teamRepository).save(teamSaving);
        assertEquals(teamExpected, teamActual);
    }

}
