package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.TeamUser;
import com.gavinfenton.quizolation.repository.TeamUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TeamUserServiceTest {

    @InjectMocks
    private TeamUserService teamUserService;

    @Mock
    private TeamUserRepository teamUserRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testAddUserToTeamCreatesEntryAndSavesToRepository() {
        // Given
        TeamUser teamUser = new TeamUser();
        teamUser.setTeamId(321L);
        teamUser.setUserId(123L);

        // When
        teamUserService.addUserToTeam(teamUser.getTeamId(), teamUser.getUserId());

        // Then
        verify(teamUserRepository).save(teamUser);
    }

}
