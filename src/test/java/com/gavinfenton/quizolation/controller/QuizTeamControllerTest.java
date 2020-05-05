package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.config.security.QuizPermissionEvaluator;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.security.UserDetailsServiceImpl;
import com.gavinfenton.quizolation.service.QuizTeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(controllers = QuizTeamController.class)
public class QuizTeamControllerTest {

    @MockBean
    private QuizTeamService quizTeamService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private QuizPermissionEvaluator quizPermissionEvaluator;

    @BeforeEach
    public void setup() {
        initMocks(this);
        ControllerTestHelper.setupHasPermissionPasses(quizPermissionEvaluator);
    }

    @Test
    public void testAddTeamToQuizCallsRepoAndReturnsNoContent() throws Exception {
        // Given
        Long quizId = 123L;
        Long teamId = 321L;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.QUIZ + Endpoints.TEAM, quizId, teamId);

        // When
        ResultActions response = mvc.perform(request);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(quizId), eq("Quiz"), eq("UPDATE"));
        verify(quizTeamService).addTeamToQuiz(quizId, teamId);
        response.andExpect(status().isNoContent());
    }

}
