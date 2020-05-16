package com.gavinfenton.quizolation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinfenton.quizolation.config.security.QuizPermissionEvaluator;
import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.UserDetailsDTO;
import com.gavinfenton.quizolation.dto.UserLoginDTO;
import com.gavinfenton.quizolation.dto.UserRegistrationDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.mapper.UserDetailsMapper;
import com.gavinfenton.quizolation.mapper.UserLoginMapper;
import com.gavinfenton.quizolation.mapper.UserRegistrationMapper;
import com.gavinfenton.quizolation.security.UserDetailsServiceImpl;
import com.gavinfenton.quizolation.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserRegistrationMapper userRegistrationMapper = UserRegistrationMapper.INSTANCE;

    private final UserLoginMapper userLoginMapper = UserLoginMapper.INSTANCE;

    private final UserDetailsMapper userDetailsMapper = UserDetailsMapper.INSTANCE;

    @MockBean
    private AppUserService appUserService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @MockBean
    private QuizPermissionEvaluator quizPermissionEvaluator;

    @BeforeEach
    public void setup() {
        initMocks(this);
        ControllerTestHelper.setupHasPermissionPasses(quizPermissionEvaluator);
    }

    @Test
    public void testRegisterUserCallsServiceAndReturnsNoContent() throws Exception {
        // Given
        UserRegistrationDTO dtoSaving = new UserRegistrationDTO();
        dtoSaving.setUsername("some-username");
        AppUser userSaving = userRegistrationMapper.toUser(dtoSaving);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.USERS + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSaving));

        // When
        ResultActions response = mvc.perform(request);

        // Then
        verify(appUserService).registerUser(userSaving);
        response.andExpect(status().isNoContent());
    }

    @Test
    public void testLoginUserCallsServiceAndReturnsUser() throws Exception {
        // Given
        UserLoginDTO dtoLogin = new UserLoginDTO();
        dtoLogin.setEmail("some-username@email.com");
        AppUser userLogin = userLoginMapper.toUser(dtoLogin);

        AppUser userLoggedIn = new AppUser();
        userLoggedIn.setUsername("some-username");
        UserDetailsDTO dtoLoggedIin = userDetailsMapper.toDTO(userLoggedIn);

        given(appUserService.loginUser(userLogin)).willReturn(userLoggedIn);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Endpoints.USERS + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoLogin));

        // When
        ResultActions response = mvc.perform(request);
        UserDetailsDTO userActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), UserDetailsDTO.class);

        // Then
        verify(appUserService).loginUser(userLogin);
        response.andExpect(status().isOk());
        assertEquals(dtoLoggedIin, userActual);
    }

    @Test
    @WithUserDetails // TODO: Fix
    public void testGetUserReturnsUserFromAuthentication() throws Exception {
        // Given
        AppUser userAuth = new AppUser();
        userAuth.setEmail("afsdf");

        SecurityContextHolder.setContext(securityContext);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(userAuth);

        AppUser userLoggedIn = new AppUser();
        userLoggedIn.setUsername("qwerty");
        UserDetailsDTO dtoLoggedIn = userDetailsMapper.toDTO(userLoggedIn);

        given(appUserService.getUserByEmail(userAuth.getEmail())).willReturn(userLoggedIn);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Endpoints.USERS);

        // When
        ResultActions response = mvc.perform(request);
        UserDetailsDTO userActual = objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), UserDetailsDTO.class);

        // Then
        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
        verify(appUserService).getUserByEmail(userAuth.getEmail());
        response.andExpect(status().isOk());
        assertEquals(dtoLoggedIn, userActual);
    }

}
