package com.gavinfenton.quizolation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.service.RoundService;
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

@WebMvcTest(controllers = RoundController.class)
public class RoundControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private RoundService roundService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateRoundCallsAndReturnsRoundFromService() throws Exception {
        // Given
        Round roundSaving = new Round();
        roundSaving.setName("Some Round");
        Long quizIdSaving = 92L;
        Round roundExpected = new Round();
        Long idExpected = 135L;
        roundExpected.setId(idExpected);
        roundExpected.setName("Some Other Round");
        given(roundService.createRound(quizIdSaving, roundSaving)).willReturn(roundExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.QUIZ_ROUNDS, quizIdSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roundSaving));

        // When
        ResultActions response = mvc.perform(request);
        Round roundActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Round.class);

        // Then
        verify(roundService).createRound(quizIdSaving, roundSaving);
        response.andExpect(status().isCreated());
        response.andExpect(header().string("Location", EndpointHelper.insertId(Endpoints.ROUND, idExpected)));
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testGetRoundCallsAndReturnsRoundFromService() throws Exception {
        // Given
        Round roundExpected = new Round();
        Long idExpected = 321L;
        roundExpected.setName("Some Round");
        given(roundService.getRound(idExpected)).willReturn(roundExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.ROUND, idExpected)
                .accept(MediaType.APPLICATION_JSON);

        // When
        ResultActions response = mvc.perform(request);
        Round roundActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Round.class);

        // Then
        verify(roundService).getRound(idExpected);
        response.andExpect(status().isOk());
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testGetRoundsCallsAndReturnsRoundsFromService() throws Exception {
        // Given
        Long quizIdExpected = 12L;
        Round roundExpected1 = new Round();
        Round roundExpected2 = new Round();
        roundExpected1.setName("Round 1");
        roundExpected2.setName("Round 2");
        List<Round> roundsExpected = Arrays.asList(roundExpected1, roundExpected2);
        given(roundService.getRounds(quizIdExpected)).willReturn(roundsExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.QUIZ_ROUNDS, quizIdExpected)
                .accept(MediaType.APPLICATION_JSON);

        // When
        ResultActions response = mvc.perform(request);
        List<Round> roundsActual = objectMapper.readValue(
                response.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        // Then
        verify(roundService).getRounds(quizIdExpected);
        response.andExpect(status().isOk());
        assertEquals(roundsExpected, roundsActual);
    }

    @Test
    public void testUpdateRoundCallsAndReturnsRoundFromService() throws Exception {
        // Given
        Round roundSaving = new Round();
        Long idSaving = 321L;
        roundSaving.setId(idSaving);
        roundSaving.setName("Some Round");
        Round roundExpected = new Round();
        roundExpected.setName("Some Other Round");
        given(roundService.updateRound(idSaving, roundSaving)).willReturn(roundExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(Endpoints.ROUND, idSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roundSaving));

        // When
        ResultActions response = mvc.perform(request);
        Round roundActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Round.class);

        // Then
        verify(roundService).updateRound(idSaving, roundSaving);
        response.andExpect(status().isOk());
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testDeleteRoundCallsService() throws Exception {
        // Given
        Long idDeleting = 321L;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Endpoints.ROUND, idDeleting);

        // When
        ResultActions response = mvc.perform(request);

        // Then
        verify(roundService).deleteRound(idDeleting);
        response.andExpect(status().isNoContent());
    }

}
