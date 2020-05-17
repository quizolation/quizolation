package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.repository.AppUserRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppUserServiceTest {

    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    public void setup() {
        initMocks(this);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetUserByEmailFindsAndReturnsFromRepository() {
        // Given
        String email = "some@email.com";
        AppUser userExpected = new AppUser();
        userExpected.setUsername("dufbso");
        given(appUserRepository.findByEmail(email)).willReturn(Optional.of(userExpected));

        // When
        AppUser userActual = appUserService.getUserByEmail(email);

        // Then
        verify(appUserRepository).findByEmail(email);
        assertEquals(userExpected, userActual);
    }

    @Test
    public void testGetUserByEmailThrowsWhenNotFound() {
        // Given
        String email = "some@email.com";
        given(appUserRepository.findByEmail(email)).willReturn(Optional.empty());

        // When / Then
        assertThrows(ObjectNotFoundException.class, () -> appUserService.getUserByEmail(email));
    }

    @Test
    public void testRegisterUserEncodesPasswordAndSavesToRepository() {
        // Given
        AppUser userRegistering = new AppUser();
        String userPassword = "213124egwfw";
        userRegistering.setPassword(userPassword);
        AppUser userEncoded = new AppUser();
        String passwordEncoded = "reallysecure";
        userEncoded.setPassword(passwordEncoded);
        given(passwordEncoder.encode(userPassword)).willReturn(passwordEncoded);

        // When
        appUserService.registerUser(userRegistering);

        // Then
        verify(passwordEncoder).encode(userPassword);
        verify(appUserRepository).save(userEncoded);
    }

    @Test
    public void testLoginUserGetsUserFromRepositoryAndSetsAuthentication() {
        // Given
        AppUser userLoggingIn = new AppUser();
        String email = "user@email.com";
        userLoggingIn.setEmail(email);
        userLoggingIn.setPassword("firstpassword");

        AppUser userExisting = new AppUser();
        userExisting.setPassword("secondpassword");
        Authentication authFromExisting = new UsernamePasswordAuthenticationToken(userExisting, userExisting.getPassword(), new HashSet<>());

        given(appUserRepository.findByEmail(email)).willReturn(Optional.of(userExisting));
        given(passwordEncoder.matches(userLoggingIn.getPassword(), userExisting.getPassword())).willReturn(true);

        // When
        AppUser userActual = appUserService.loginUser(userLoggingIn);

        // Then
        verify(appUserRepository).findByEmail(email);
        verify(passwordEncoder).matches(userLoggingIn.getPassword(), userExisting.getPassword());
        verify(securityContext).setAuthentication(authFromExisting);
        assertEquals(userExisting, userActual);
    }

    @Test
    public void testLoginThrowsWhenPasswordsDoNotMatch() {
        // Given
        AppUser userLoggingIn = new AppUser();
        String email = "user@email.com";
        userLoggingIn.setEmail(email);
        userLoggingIn.setPassword("firstpassword");

        AppUser userExisting = new AppUser();
        userExisting.setPassword("secondpassword");
        Authentication authFromExisting = new UsernamePasswordAuthenticationToken(userExisting, userExisting.getPassword(), new HashSet<>());

        given(appUserRepository.findByEmail(email)).willReturn(Optional.of(userExisting));
        given(passwordEncoder.matches(userLoggingIn.getPassword(), userExisting.getPassword())).willReturn(false);

        // When / Then
        assertThrows(ObjectNotFoundException.class, () -> {
            appUserService.loginUser(userLoggingIn);
        });

        verify(appUserRepository).findByEmail(email);
        verify(passwordEncoder).matches(userLoggingIn.getPassword(), userExisting.getPassword());
        verify(securityContext, never()).setAuthentication(authFromExisting);
    }

}
