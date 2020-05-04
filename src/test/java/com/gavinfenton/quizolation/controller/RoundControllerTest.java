package com.gavinfenton.quizolation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinfenton.quizolation.config.security.QuizPermissionEvaluator;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.RoundDTO;
import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.mapper.RoundMapper;
import com.gavinfenton.quizolation.security.UserDetailsServiceImpl;
import com.gavinfenton.quizolation.service.RoundService;
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
@WebMvcTest(controllers = RoundController.class)
public class RoundControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RoundMapper roundMapper = RoundMapper.INSTANCE;

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
    public void testCreateRoundCallsAndReturnsRoundFromService() throws Exception {
        // Given
        RoundDTO dtoSaving = new RoundDTO();
        dtoSaving.setName("Some Round");
        Long quizIdSaving = 92L;
        Round roundSaving = roundMapper.toRound(dtoSaving);

        Round roundSaved = new Round();
        Long idExpected = 135L;
        roundSaved.setId(idExpected);
        roundSaved.setName("Some Other Round");

        given(roundService.createRound(quizIdSaving, roundSaving)).willReturn(roundSaved);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.QUIZ + Endpoints.ROUNDS, quizIdSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);
        RoundDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), RoundDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(quizIdSaving), eq("Quiz"), eq("UPDATE"));
        verify(roundService).createRound(quizIdSaving, roundSaving);
        response.andExpect(status().isCreated());
        response.andExpect(header().string("Location", EndpointHelper.insertId(Endpoints.ROUND, idExpected)));
        assertMappedDTOEqualsRound(roundSaved, dtoActual);
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
        RoundDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), RoundDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idExpected), eq("Round"), eq("READ"));
        verify(roundService).getRound(idExpected);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsRound(roundExpected, dtoActual);
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
                .get(Endpoints.QUIZ + Endpoints.ROUNDS, quizIdExpected)
                .accept(MediaType.APPLICATION_JSON);

        // When
        ResultActions response = mvc.perform(request);
        List<RoundDTO> roundsActual = objectMapper.readValue(
                response.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(quizIdExpected), eq("Quiz"), eq("READ"));
        verify(roundService).getRounds(quizIdExpected);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsRound(roundExpected1, roundsActual.get(0));
        assertMappedDTOEqualsRound(roundExpected2, roundsActual.get(1));
    }

    @Test
    public void testUpdateRoundCallsAndReturnsRoundFromService() throws Exception {
        // Given
        RoundDTO dtoSaving = new RoundDTO();
        Long idSaving = 321L;
        dtoSaving.setId(idSaving);
        dtoSaving.setName("Some Round");
        Round roundSaving = roundMapper.toRound(dtoSaving);

        Round roundSaved = new Round();
        roundSaved.setName("Some Other Round");
        given(roundService.updateRound(idSaving, roundSaving)).willReturn(roundSaved);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(Endpoints.ROUND, idSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);
        RoundDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), RoundDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idSaving), eq("Round"), eq("UPDATE"));
        verify(roundService).updateRound(idSaving, roundSaving);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsRound(roundSaved, dtoActual);
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
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idDeleting), eq("Round"), eq("DELETE"));
        verify(roundService).deleteRound(idDeleting);
        response.andExpect(status().isNoContent());
    }

    public void assertMappedDTOEqualsRound(Round expected, RoundDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

}
