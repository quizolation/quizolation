package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TeamEvaluatorTest {

    @InjectMocks
    private TeamEvaluator teamEvaluator;

    @Mock
    private TeamService teamService;

    private AppUser appUser;
    private Long userId;
    private Team team;
    private Long teamId;

    @BeforeEach
    public void setup() {
        initMocks(this);

        appUser = new AppUser();
        userId = 9876543210L;
        appUser.setId(userId);
        team = new Team();
        teamId = 1234567890L;
        team.setId(teamId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CREATE", "UPDATE", "DELETE"})
    public void testCUDOperationsCheckUserIsTeamOwner(String permission) {
        // Given appUser, team, teamId, permission and...
        team.setLeaderId(userId);
        given(teamService.getTeam(teamId)).willReturn(team);

        //When
        boolean actual1 = teamEvaluator.hasPermission(appUser, teamId, permission);
        boolean actual2 = teamEvaluator.hasPermission(appUser, team, permission);
        boolean actual3 = teamEvaluator.hasPermission(appUser, (Object) team, permission);

        // Then
        verify(teamService, times(3)).getTeam(teamId);
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testROperationReturnsTrueWithoutCheck() {
        // Given appUser, team, teamId and...
        String permission = "READ";
        team.setLeaderId(userId);
        given(teamService.getTeam(teamId)).willReturn(team);

        //When
        boolean actual1 = teamEvaluator.hasPermission(appUser, teamId, permission);
        boolean actual2 = teamEvaluator.hasPermission(appUser, team, permission);
        boolean actual3 = teamEvaluator.hasPermission(appUser, (Object) team, permission);

        // Then
        verify(teamService, never()).getTeam(teamId);
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testUnknownOperationReturnsFalseWithNoCalls() {
        // Given appUser, team, teamId and...
        String permission = "ASDFGH";

        //When
        boolean actual1 = teamEvaluator.hasPermission(appUser, teamId, permission);
        boolean actual2 = teamEvaluator.hasPermission(appUser, team, permission);
        boolean actual3 = teamEvaluator.hasPermission(appUser, (Object) team, permission);

        // Then
        verify(teamService, never()).getTeam(any());
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
    }

}
