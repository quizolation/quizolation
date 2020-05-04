package com.gavinfenton.quizolation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinfenton.quizolation.config.security.QuizPermissionEvaluator;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.QuizDetailsDTO;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.mapper.QuizDetailsMapper;
import com.gavinfenton.quizolation.security.UserDetailsServiceImpl;
import com.gavinfenton.quizolation.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
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

@WithMockUser
@WebMvcTest(controllers = QuizController.class)
public class QuizControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final QuizDetailsMapper quizDetailsMapper = QuizDetailsMapper.INSTANCE;

    @MockBean
    private QuizService quizService;

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
    public void testCreateQuizCallsAndReturnsQuizFromService() throws Exception {
        // Given
        QuizDetailsDTO dtoSaving = new QuizDetailsDTO();
        dtoSaving.setName("Some Quiz");
        Quiz quizSaving = quizDetailsMapper.toQuiz(dtoSaving);

        Quiz quizSaved = new Quiz();
        Long idExpected = 135L;
        quizSaved.setId(idExpected);
        quizSaved.setName("Some Other Quiz");

        given(quizService.createQuiz(quizSaving)).willReturn(quizSaved);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.QUIZZES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);
        QuizDetailsDTO quizActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), QuizDetailsDTO.class);

        // Then
        verify(quizService).createQuiz(quizSaving);
        response.andExpect(status().isCreated());
        response.andExpect(header().string("Location", Endpoints.QUIZZES + "/" + idExpected));
        assertMappedDTOEqualsQuiz(quizSaved, quizActual);
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
        QuizDetailsDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), QuizDetailsDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idExpected), eq("Quiz"), eq("READ"));
        verify(quizService).getQuiz(idExpected);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsQuiz(quizExpected, dtoActual);
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
        List<QuizDetailsDTO> dtosActual = objectMapper.readValue(
                response.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        // Then
        verify(quizService).getQuizzes();
        response.andExpect(status().isOk());
        assertMappedDTOEqualsQuiz(quizExpected1, dtosActual.get(0));
        assertMappedDTOEqualsQuiz(quizExpected2, dtosActual.get(1));
    }

    @Test
    public void testUpdateQuizCallsAndReturnsQuizFromService() throws Exception {
        // Given
        QuizDetailsDTO dtoSaving = new QuizDetailsDTO();
        Long idSaving = 321L;
        dtoSaving.setId(idSaving);
        dtoSaving.setName("Some Quiz");
        Quiz quizSaving = quizDetailsMapper.toQuiz(dtoSaving);

        Quiz quizSaved = new Quiz();
        quizSaved.setName("Some Other Quiz");

        given(quizService.updateQuiz(idSaving, quizSaving)).willReturn(quizSaved);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(Endpoints.QUIZ, idSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);
        QuizDetailsDTO quizActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), QuizDetailsDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idSaving), eq("Quiz"), eq("UPDATE"));
        verify(quizService).updateQuiz(idSaving, quizSaving);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsQuiz(quizSaved, quizActual);
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
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idDeleting), eq("Quiz"), eq("DELETE"));
        verify(quizService).deleteQuiz(idDeleting);
        response.andExpect(status().isNoContent());
    }

    private void assertMappedDTOEqualsQuiz(Quiz expected, QuizDetailsDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

}
