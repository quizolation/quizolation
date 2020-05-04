package com.gavinfenton.quizolation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinfenton.quizolation.config.security.QuizPermissionEvaluator;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.QuestionAndAnswerDTO;
import com.gavinfenton.quizolation.dto.QuestionDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.mapper.QuestionAndAnswerMapper;
import com.gavinfenton.quizolation.mapper.QuestionMapper;
import com.gavinfenton.quizolation.security.UserDetailsServiceImpl;
import com.gavinfenton.quizolation.service.QuestionService;
import com.gavinfenton.quizolation.service.RoundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)
public class QuestionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final QuestionAndAnswerMapper questionAndAnswerMapper = QuestionAndAnswerMapper.INSTANCE;

    private final QuestionMapper questionMapper = QuestionMapper.INSTANCE;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private RoundService roundService;

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
    @WithMockUser
    public void testCreateQuestionCallsAndReturnsQuestionFromService() throws Exception {
        // Given
        QuestionAndAnswerDTO dtoSaving = new QuestionAndAnswerDTO();
        String question = "Some Question";
        dtoSaving.setQuestion(question);
        Long roundIdSaving = 92L;
        Question questionSaving = new Question();
        questionSaving.setQuestion(question);

        Question questionSaved = new Question();
        Long idExpected = 135L;
        questionSaved.setId(idExpected);
        questionSaved.setQuestion("Some Other Question");

        given(questionService.createQuestion(roundIdSaving, questionSaving)).willReturn(questionSaved);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.ROUND + Endpoints.QUESTIONS, roundIdSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);
        QuestionAndAnswerDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), QuestionAndAnswerDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(roundIdSaving), eq("Round"), eq("UPDATE"));
        verify(questionService).createQuestion(roundIdSaving, questionSaving);
        response.andExpect(status().isCreated());
        response.andExpect(header().string("Location", EndpointHelper.insertId(Endpoints.QUESTION, idExpected)));
        assertMappedDTOEqualsQuestion(questionSaved, dtoActual);
    }

    @Test
    public void testGetQuestionCallsAndReturnsQuestionAndAnswerFromServiceForMaster() throws Exception {
        // Given
        Question questionExpected = new Question();
        Long idExpected = 321L;
        questionExpected.setQuestion("Some Question");
        given(questionService.getQuestion(idExpected)).willReturn(questionExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.QUESTION, idExpected)
                .accept(MediaType.APPLICATION_JSON);

        Long userId = 54321L;
        mockUserWithId(userId);
        given(questionService.isMasterOfRelatedQuiz(idExpected, userId)).willReturn(true);

        // When
        ResultActions response = mvc.perform(request);
        QuestionAndAnswerDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), QuestionAndAnswerDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idExpected), eq("Question"), eq("READ"));
        verify(questionService).getQuestion(idExpected);
        verify(questionService).isMasterOfRelatedQuiz(idExpected, userId);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsQuestion(questionExpected, dtoActual);
    }

    @Test
    public void testGetQuestionsCallsAndReturnsQuestionAndAnswersFromServiceForMaster() throws Exception {
        // Given
        Long roundIdExpected = 12L;
        Question questionExpected1 = new Question();
        Question questionExpected2 = new Question();
        questionExpected1.setQuestion("Question 1");
        questionExpected2.setQuestion("Question 2");
        List<Question> questionsExpected = Arrays.asList(questionExpected1, questionExpected2);
        given(questionService.getQuestions(roundIdExpected)).willReturn(questionsExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.ROUND + Endpoints.QUESTIONS, roundIdExpected)
                .accept(MediaType.APPLICATION_JSON);

        Long userId = 54321L;
        mockUserWithId(userId);
        given(roundService.isMasterOfRelatedQuiz(roundIdExpected, userId)).willReturn(true);

        // When
        ResultActions response = mvc.perform(request);
        List<QuestionAndAnswerDTO> dtosActual = objectMapper.readValue(
                response.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(roundIdExpected), eq("Round"), eq("READ"));
        verify(questionService).getQuestions(roundIdExpected);
        verify(roundService).isMasterOfRelatedQuiz(roundIdExpected, userId);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsQuestion(questionExpected1, dtosActual.get(0));
        assertMappedDTOEqualsQuestion(questionExpected2, dtosActual.get(1));
    }

    @Test
    @WithMockUser
    public void testUpdateQuestionCallsAndReturnsQuestionFromService() throws Exception {
        // Given
        QuestionAndAnswerDTO dtoSaving = new QuestionAndAnswerDTO();
        Long idSaving = 321L;
        String question = "Some Question";
        dtoSaving.setId(idSaving);
        dtoSaving.setQuestion(question);
        Question questionSaving = new Question();
        questionSaving.setId(idSaving);
        questionSaving.setQuestion(question);

        Question questionSaved = new Question();
        questionSaved.setQuestion("Some Other Question");

        given(questionService.updateQuestion(idSaving, questionSaving)).willReturn(questionSaved);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(Endpoints.QUESTION, idSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);
        QuestionAndAnswerDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), QuestionAndAnswerDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idSaving), eq("Question"), eq("UPDATE"));
        verify(questionService).updateQuestion(idSaving, questionSaving);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsQuestion(questionSaved, dtoActual);
    }

    @Test
    @WithMockUser
    public void testDeleteQuestionCallsService() throws Exception {
        // Given
        Long idDeleting = 321L;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Endpoints.QUESTION, idDeleting);

        // When
        ResultActions response = mvc.perform(request);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idDeleting), eq("Question"), eq("DELETE"));
        verify(questionService).deleteQuestion(idDeleting);
        response.andExpect(status().isNoContent());
    }

    private void assertMappedDTOEqualsQuestion(Question expected, QuestionDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getQuestion(), actual.getQuestion());
        assertEquals(expected.getPoints(), actual.getPoints());
    }

    private void assertMappedDTOEqualsQuestion(Question expected, QuestionAndAnswerDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getQuestion(), actual.getQuestion());
        assertEquals(expected.getPoints(), actual.getPoints());
        assertEquals(expected.getAnswer(), actual.getAnswer());
    }

    private void mockUserWithId(Long userId) {
        AppUser appUser = new AppUser();
        appUser.setId(userId);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(appUser);

        SecurityContextHolder.setContext(securityContext);
    }

}
