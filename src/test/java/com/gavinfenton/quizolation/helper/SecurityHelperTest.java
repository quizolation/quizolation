package com.gavinfenton.quizolation.helper;

import com.gavinfenton.quizolation.entity.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class SecurityHelperTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private AppUser appUser;
    private Long userId;

    @BeforeEach
    public void setup() {
        initMocks(this);

        appUser = new AppUser();
        userId = 564L;
        appUser.setId(userId);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetUserReturnsPrincipal() {
        // Given
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(appUser);

        // When
        AppUser result = SecurityHelper.getUser();

        // Then
        assertEquals(appUser, result);
        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
    }

    @Test
    public void testGetUserReturnsIdFromPrincipal() {
        // Given
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(appUser);

        // When
        Long result = SecurityHelper.getUserId();

        // Then
        assertEquals(userId, result);
        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
    }

}
