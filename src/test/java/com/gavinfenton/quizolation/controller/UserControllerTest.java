package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.config.security.QuizPermissionEvaluator;
import com.gavinfenton.quizolation.security.UserDetailsServiceImpl;
import com.gavinfenton.quizolation.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.MockitoAnnotations.initMocks;

@WithMockUser
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @MockBean
    private AppUserService appUserService;

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
    public void testRegisterUserCallsServiceAndReturnsNoContent() {
        fail();
    }

}
