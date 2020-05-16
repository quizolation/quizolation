package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.service.RoundService;
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

public class RoundEvaluatorTest {

    @InjectMocks
    private RoundEvaluator roundEvaluator;

    @Mock
    private RoundService roundService;

    private AppUser appUser;
    private Long userId;
    private Round round;
    private Long roundId;

    @BeforeEach
    public void setup() {
        initMocks(this);

        appUser = new AppUser();
        userId = 9876543210L;
        appUser.setId(userId);
        round = new Round();
        roundId = 1234567890L;
        round.setId(roundId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CREATE", "UPDATE", "DELETE"})
    public void testCUDOperationsCheckUserIsQuizMaster(String permission) {
        // Given appUser, round, roundId, permission and...
        given(roundService.isMasterOfRelatedQuiz(roundId, userId)).willReturn(true);

        //When
        boolean actual1 = roundEvaluator.hasPermission(appUser, roundId, permission);
        boolean actual2 = roundEvaluator.hasPermission(appUser, round, permission);
        boolean actual3 = roundEvaluator.hasPermission(appUser, (Object) round, permission);

        // Then
        verify(roundService, times(3)).isMasterOfRelatedQuiz(roundId, userId);
        verify(roundService, never()).isTeamMemberOfRelatedQuiz(any(), any());
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testROperationCheckUserIsQuizMasterOrTeamMember() {
        // Given appUser, round, roundId and...
        String permission = "READ";
        given(roundService.isMasterOfRelatedQuiz(roundId, userId)).willReturn(false);
        given(roundService.isTeamMemberOfRelatedQuiz(roundId, userId)).willReturn(true);

        //When
        boolean actual1 = roundEvaluator.hasPermission(appUser, roundId, permission);
        boolean actual2 = roundEvaluator.hasPermission(appUser, round, permission);
        boolean actual3 = roundEvaluator.hasPermission(appUser, (Object) round, permission);

        // Then
        verify(roundService, times(3)).isMasterOfRelatedQuiz(roundId, userId);
        verify(roundService, times(3)).isTeamMemberOfRelatedQuiz(roundId, userId);
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testUnknownOperationReturnsFalseWithNoCalls() {
        // Given appUser, round, roundId and...
        String permission = "ASDFGH";

        //When
        boolean actual1 = roundEvaluator.hasPermission(appUser, roundId, permission);
        boolean actual2 = roundEvaluator.hasPermission(appUser, round, permission);
        boolean actual3 = roundEvaluator.hasPermission(appUser, (Object) round, permission);

        // Then
        verify(roundService, never()).isMasterOfRelatedQuiz(any(), any());
        verify(roundService, never()).isTeamMemberOfRelatedQuiz(any(), any());
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
    }

}
