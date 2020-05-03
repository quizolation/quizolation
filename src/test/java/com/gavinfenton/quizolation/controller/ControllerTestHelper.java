package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.config.security.QuizPermissionEvaluator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class ControllerTestHelper {

    public static void setupHasPermissionPasses(QuizPermissionEvaluator quizPermissionEvaluator) {
        given(quizPermissionEvaluator.hasPermission(any(), any(), any())).willReturn(true);
        given(quizPermissionEvaluator.hasPermission(any(), any(), any(), any())).willReturn(true);
    }

}
