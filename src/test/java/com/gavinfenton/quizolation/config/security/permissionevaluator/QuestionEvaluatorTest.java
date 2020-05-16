package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.service.QuestionService;
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

public class QuestionEvaluatorTest {

    @InjectMocks
    private QuestionEvaluator questionEvaluator;

    @Mock
    private QuestionService questionService;

    private AppUser appUser;
    private Long userId;
    private Question question;
    private Long questionId;

    @BeforeEach
    public void setup() {
        initMocks(this);

        appUser = new AppUser();
        userId = 9876543210L;
        appUser.setId(userId);
        question = new Question();
        questionId = 1234567890L;
        question.setId(questionId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CREATE", "UPDATE", "DELETE"})
    public void testCUDOperationsCheckUserIsQuizMaster(String permission) {
        // Given appUser, question, questionId, permission and...
        given(questionService.isMasterOfRelatedQuiz(questionId, userId)).willReturn(true);

        //When
        boolean actual1 = questionEvaluator.hasPermission(appUser, questionId, permission);
        boolean actual2 = questionEvaluator.hasPermission(appUser, question, permission);
        boolean actual3 = questionEvaluator.hasPermission(appUser, (Object) question, permission);

        // Then
        verify(questionService, times(3)).isMasterOfRelatedQuiz(questionId, userId);
        verify(questionService, never()).isTeamMemberOfRelatedQuiz(any(), any());
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testROperationCheckUserIsQuizMasterOrTeamMember() {
        // Given appUser, question, questionId and...
        String permission = "READ";
        given(questionService.isMasterOfRelatedQuiz(questionId, userId)).willReturn(false);
        given(questionService.isTeamMemberOfRelatedQuiz(questionId, userId)).willReturn(true);

        //When
        boolean actual1 = questionEvaluator.hasPermission(appUser, questionId, permission);
        boolean actual2 = questionEvaluator.hasPermission(appUser, question, permission);
        boolean actual3 = questionEvaluator.hasPermission(appUser, (Object) question, permission);

        // Then
        verify(questionService, times(3)).isMasterOfRelatedQuiz(questionId, userId);
        verify(questionService, times(3)).isTeamMemberOfRelatedQuiz(questionId, userId);
        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
    }

    @Test
    public void testUnknownOperationReturnsFalseWithNoCheck() {
        // Given appUser, question, questionId and...
        String permission = "ASDFGH";

        //When
        boolean actual1 = questionEvaluator.hasPermission(appUser, questionId, permission);
        boolean actual2 = questionEvaluator.hasPermission(appUser, question, permission);
        boolean actual3 = questionEvaluator.hasPermission(appUser, (Object) question, permission);

        // Then
        verify(questionService, never()).isMasterOfRelatedQuiz(any(), any());
        verify(questionService, never()).isTeamMemberOfRelatedQuiz(any(), any());
        assertFalse(actual1);
        assertFalse(actual2);
        assertFalse(actual3);
    }

}
