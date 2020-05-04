package com.gavinfenton.quizolation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinfenton.quizolation.config.security.QuizPermissionEvaluator;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.TeamDTO;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.mapper.TeamMapper;
import com.gavinfenton.quizolation.security.UserDetailsServiceImpl;
import com.gavinfenton.quizolation.service.TeamService;
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
@WebMvcTest(controllers = TeamController.class)
public class TeamControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TeamMapper teamMapper = TeamMapper.INSTANCE;

    @MockBean
    private TeamService teamService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private QuizPermissionEvaluator quizPermissionEvaluator;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        ControllerTestHelper.setupHasPermissionPasses(quizPermissionEvaluator);
    }

    @Test
    public void testGetTeamCallsAndReturnsQuizFromService() throws Exception {
        // Given
        Team teamExpected = new Team();
        Long idExpected = 1L;
        teamExpected.setName("Team 1");

        given(teamService.getTeam(idExpected)).willReturn(teamExpected);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(Endpoints.TEAM, idExpected)
                .accept(MediaType.APPLICATION_JSON);

        // When
        ResultActions response = mvc.perform(requestBuilder);
        TeamDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), TeamDTO.class);

        // Then
        verify(teamService).getTeam(idExpected);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsTeam(teamExpected, dtoActual);
    }

    @Test
    public void testCreateTeamCallsAndReturnsTeamFromService() throws Exception {
        // Given
        TeamDTO dtoSaving = new TeamDTO();
        dtoSaving.setName("Some Team");
        Team teamSaving = teamMapper.toTeam(dtoSaving);

        Team teamExpected = new Team();
        Long idExpected = 135L;
        teamExpected.setId(idExpected);
        teamExpected.setName("Some Other Team");
        given(teamService.createTeam(teamSaving)).willReturn(teamExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.TEAMS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);
        TeamDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), TeamDTO.class);

        // Then
        verify(teamService).createTeam(teamSaving);
        response.andExpect(status().isCreated());
        response.andExpect(header().string("Location", Endpoints.TEAMS + "/" + idExpected));
        assertMappedDTOEqualsTeam(teamExpected, dtoActual);
    }

    @Test
    public void testGetTeamsCallsAndReturnsTeamsFromService() throws Exception {
        // Given
        Team teamExpected1 = new Team();
        Team teamExpected2 = new Team();
        teamExpected1.setName("Team 1");
        teamExpected2.setName("Team 2");
        List<Team> teamsExpected = Arrays.asList(teamExpected1, teamExpected2);
        given(teamService.getTeams()).willReturn(teamsExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.TEAMS)
                .accept(MediaType.APPLICATION_JSON);

        // When
        ResultActions response = mvc.perform(request);
        List<TeamDTO> dtosActual = objectMapper.readValue(
                response.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        // Then
        verify(teamService).getTeams();
        response.andExpect(status().isOk());
        assertMappedDTOEqualsTeam(teamExpected1, dtosActual.get(0));
        assertMappedDTOEqualsTeam(teamExpected2, dtosActual.get(1));
    }

    @Test
    public void testUpdateTeamCallsAndReturnsTeamFromService() throws Exception {
        // Given
        TeamDTO dtoSaving = new TeamDTO();
        Long idSaving = 321L;
        dtoSaving.setId(idSaving);
        dtoSaving.setName("Some Team");
        Team teamSaving = teamMapper.toTeam(dtoSaving);

        Team teamExpected = new Team();
        teamExpected.setName("Some Other Team");

        given(teamService.updateTeam(idSaving, teamSaving)).willReturn(teamExpected);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(Endpoints.TEAM, idSaving)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);
        TeamDTO dtoActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), TeamDTO.class);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idSaving), eq("Team"), eq("UPDATE"));
        verify(teamService).updateTeam(idSaving, teamSaving);
        response.andExpect(status().isOk());
        assertMappedDTOEqualsTeam(teamExpected, dtoActual);
    }

    @Test
    public void testDeleteQuizCallsService() throws Exception {
        // Given
        Long idDeleting = 321L;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Endpoints.TEAM, idDeleting);

        // When
        ResultActions response = mvc.perform(request);

        // Then
        verify(quizPermissionEvaluator).hasPermission(any(Authentication.class), eq(idDeleting), eq("Team"), eq("DELETE"));
        verify(teamService).deleteTeam(idDeleting);
        response.andExpect(status().isNoContent());
    }

    private void assertMappedDTOEqualsTeam(Team expected, TeamDTO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

}
