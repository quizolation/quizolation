package com.gavinfenton.quizolation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.service.QuizService;
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
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuizController.class)
public class QuizControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private QuizService quizService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateQuizCallsAndReturnsQuizFromService() throws Exception {
        // Given
        Quiz quizSaving = new Quiz();
        quizSaving.setName("Some Quiz");
        Quiz quizExpected = new Quiz();
        Long idExpected = 135L;
        quizExpected.setId(idExpected);
        quizExpected.setName("Some Other Quiz");
        given(quizService.createQuiz(quizSaving)).willReturn(quizExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.QUIZZES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizSaving));

        // When
        ResultActions response = mvc.perform(request);
        Quiz quizActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Quiz.class);

        // Then
        verify(quizService).createQuiz(quizSaving);
        response.andExpect(status().isCreated());
        response.andExpect(header().string("Location", Endpoints.QUIZZES + "/" + idExpected));
        assertEquals(quizExpected, quizActual);
    }

    @Test
    public void testGetQuizCallsAndReturnsQuizFromService() throws Exception {
        // Given
        Quiz quizExpected = new Quiz();
        Long idExpected = 321L;
        quizExpected.setName("Some Quiz");
        given(quizService.getQuiz(idExpected)).willReturn(quizExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.QUIZ, idExpected)
                .accept(MediaType.APPLICATION_JSON);

        // When
        ResultActions response = mvc.perform(request);
        Quiz quizActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Quiz.class);

        // Then
        verify(quizService).getQuiz(idExpected);
        response.andExpect(status().isOk());
        assertEquals(quizExpected, quizActual);
    }

    @Test
    public void testGetQuizzesCallsAndReturnsQuizzesFromService() throws Exception {
        // Given
        Quiz quizExpected1 = new Quiz();
        Quiz quizExpected2 = new Quiz();
        quizExpected1.setName("Quiz 1");
        quizExpected2.setName("Quiz 2");
        List<Quiz> quizzesExpected = Arrays.asList(quizExpected1, quizExpected2);
        given(quizService.getQuizzes()).willReturn(quizzesExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.QUIZZES)
                .accept(MediaType.APPLICATION_JSON);

        // When
        ResultActions response = mvc.perform(request);
        List<Quiz> quizzesActual = objectMapper.readValue(
                response.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        // Then
        verify(quizService).getQuizzes();
        response.andExpect(status().isOk());
        assertEquals(quizzesExpected, quizzesActual);
    }

    @Test
    public void testUpdateQuizCallsAndReturnsQuizFromService() throws Exception {
        // Given
        Quiz quizSaving = new Quiz();
        Long idSaving = 321L;
        quizSaving.setId(idSaving);
        quizSaving.setName("Some Quiz");
        Quiz quizExpected = new Quiz();
        quizExpected.setName("Some Other Quiz");
        given(quizService.updateQuiz(idSaving, quizSaving)).willReturn(quizExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(Endpoints.QUIZ, idSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quizSaving));

        // When
        ResultActions response = mvc.perform(request);
        Quiz quizActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Quiz.class);

        // Then
        verify(quizService).updateQuiz(idSaving, quizSaving);
        response.andExpect(status().isOk());
        assertEquals(quizExpected, quizActual);
    }

    @Test
    public void testDeleteQuizCallsService() throws Exception {
        // Given
        Long idDeleting = 321L;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Endpoints.QUIZ, idDeleting);

        // When
        ResultActions response = mvc.perform(request);

        // Then
        verify(quizService).deleteQuiz(idDeleting);
        response.andExpect(status().isNoContent());
    }

    @Test
    public void testAddingTeamToQuizControllerCallsAndReturnsQuizFromService() throws Exception {
        fail();
//        //Given
//        Long quizIdSaving = 32L;
//        Long teamIdSaving = 23L;
//        Quiz quizExpected = new Quiz();
//        quizExpected.setName("Quiz name");
//        given(quizService.addTeamToQuiz(quizIdSaving, teamIdSaving)).willReturn(quizExpected);
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(Endpoints.QUIZ + Endpoints.TEAM, quizIdSaving, teamIdSaving);
//
//        //When
//        ResultActions response = mvc.perform(request);
//        Quiz quizActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Quiz.class);
//
//        //Then
//        verify(quizService).addTeamToQuiz(quizIdSaving, teamIdSaving);
//        assertEquals(quizExpected, quizActual);
//        response.andExpect(status().isOk());
    }

}
