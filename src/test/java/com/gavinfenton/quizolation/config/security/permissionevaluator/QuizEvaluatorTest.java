package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.service.QuizService;
import com.gavinfenton.quizolation.service.QuizTeamService;
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

public class QuizEvaluatorTest {

    @InjectMocks
    private QuizEvaluator quizEvaluator;

    @Mock
    private QuizService quizService;

    @Mock
    private QuizTeamService quizTeamService;

    private AppUser appUser;
    private Long userId;
    private Quiz quiz;
    private Long quizId;

    @BeforeEach
    public void setup() {
        initMocks(this);

        appUser = new AppUser();
        userId = 9876543210L;
        appUser.setId(userId);
        quiz = new Quiz();
        quizId = 1234567890L;
        quiz.setId(quizId);
    }

    @Test
    public void testCOperationReturnsTrueWithoutCheck() {
        // Given appUser, quiz, quizId and...
        String permission = "CREATE";

        //When
        boolean actual1 = quizEvaluator.hasPermission(appUser, quizId, permission);
        boolean actual2 = quizEvaluator.hasPermission(appUser, quiz, permission);
        boolean actual3 = quizEvaluator.hasPermission(appUser, (Object) quiz, permission);

        // Then
        verify(quizService, never()).getQuiz(any());
        verify(quizTeamService, never()).isQuizTeamMember(any(), any());
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @ParameterizedTest
    @ValueSource(strings = {"UPDATE", "DELETE"})
    public void testUDOperationsCheckUserIsQuizMaster(String permission) {
        // Given appUser, quiz, quizId, permission and...
        quiz.setMasterId(userId);
        given(quizService.getQuiz(quizId)).willReturn(quiz);

        //When
        boolean actual1 = quizEvaluator.hasPermission(appUser, quizId, permission);
        boolean actual2 = quizEvaluator.hasPermission(appUser, quiz, permission);
        boolean actual3 = quizEvaluator.hasPermission(appUser, (Object) quiz, permission);

        // Then
        verify(quizService, times(3)).getQuiz(quizId);
        verify(quizTeamService, never()).isQuizTeamMember(any(), any());
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testROperationCheckUserIsQuizMasterOrTeamMember() {
        // Given appUser, quiz, quizId and...
        String permission = "READ";
        quiz.setMasterId(678L);
        given(quizService.getQuiz(quizId)).willReturn(quiz);
        given(quizTeamService.isQuizTeamMember(quizId, userId)).willReturn(true);

        //When
        boolean actual1 = quizEvaluator.hasPermission(appUser, quizId, permission);
        boolean actual2 = quizEvaluator.hasPermission(appUser, quiz, permission);
        boolean actual3 = quizEvaluator.hasPermission(appUser, (Object) quiz, permission);

        // Then
        verify(quizService, times(3)).getQuiz(quizId);
        verify(quizTeamService, times(3)).isQuizTeamMember(quizId, userId);
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testUnknownOperationReturnsFalseWithNoCalls() {
        // Given appUser, quiz, quizId and...
        String permission = "ASDFGH";

        //When
        boolean actual1 = quizEvaluator.hasPermission(appUser, quizId, permission);
        boolean actual2 = quizEvaluator.hasPermission(appUser, quiz, permission);
        boolean actual3 = quizEvaluator.hasPermission(appUser, (Object) quiz, permission);

        // Then
        verify(quizService, never()).getQuiz(any());
        verify(quizTeamService, never()).isQuizTeamMember(any(), any());
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
    }

}
