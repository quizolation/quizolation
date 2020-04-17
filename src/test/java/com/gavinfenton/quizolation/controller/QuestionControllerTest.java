package com.gavinfenton.quizolation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class)
public class QuestionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private QuestionService questionService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateQuestionCallsAndReturnsQuestionFromService() throws Exception {
        // Given
        Question questionSaving = new Question();
        questionSaving.setQuestion("Some Question");
        Long roundIdSaving = 92L;
        Question questionExpected = new Question();
        Long idExpected = 135L;
        questionExpected.setId(idExpected);
        questionExpected.setQuestion("Some Other Question");
        given(questionService.createQuestion(roundIdSaving, questionSaving)).willReturn(questionExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.ROUND + Endpoints.QUESTIONS, roundIdSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionSaving));

        // When
        ResultActions response = mvc.perform(request);
        Question questionActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Question.class);

        // Then
        verify(questionService).createQuestion(roundIdSaving, questionSaving);
        response.andExpect(status().isCreated());
        response.andExpect(header().string("Location", EndpointHelper.insertId(Endpoints.QUESTION, idExpected)));
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testGetQuestionCallsAndReturnsQuestionFromService() throws Exception {
        // Given
        Question questionExpected = new Question();
        Long idExpected = 321L;
        questionExpected.setQuestion("Some Question");
        given(questionService.getQuestion(idExpected)).willReturn(questionExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.QUESTION, idExpected)
                .accept(MediaType.APPLICATION_JSON);

        // When
        ResultActions response = mvc.perform(request);
        Question questionActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Question.class);

        // Then
        verify(questionService).getQuestion(idExpected);
        response.andExpect(status().isOk());
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testGetQuestionsCallsAndReturnsQuestionsFromService() throws Exception {
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

        // When
        ResultActions response = mvc.perform(request);
        List<Question> questionsActual = objectMapper.readValue(
                response.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        // Then
        verify(questionService).getQuestions(roundIdExpected);
        response.andExpect(status().isOk());
        assertEquals(questionsExpected, questionsActual);
    }

    @Test
    public void testUpdateQuestionCallsAndReturnsQuestionFromService() throws Exception {
        // Given
        Question questionSaving = new Question();
        Long idSaving = 321L;
        questionSaving.setId(idSaving);
        questionSaving.setQuestion("Some Question");
        Question questionExpected = new Question();
        questionExpected.setQuestion("Some Other Question");
        given(questionService.updateQuestion(idSaving, questionSaving)).willReturn(questionExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(Endpoints.QUESTION, idSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionSaving));

        // When
        ResultActions response = mvc.perform(request);
        Question questionActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Question.class);

        // Then
        verify(questionService).updateQuestion(idSaving, questionSaving);
        response.andExpect(status().isOk());
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testDeleteQuestionCallsService() throws Exception {
        // Given
        Long idDeleting = 321L;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Endpoints.QUESTION, idDeleting);

        // When
        ResultActions response = mvc.perform(request);

        // Then
        verify(questionService).deleteQuestion(idDeleting);
        response.andExpect(status().isNoContent());
    }

}
