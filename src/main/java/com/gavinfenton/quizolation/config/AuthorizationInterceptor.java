package com.gavinfenton.quizolation.config;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        boolean containsAuthorize = Arrays
                .stream(((HandlerMethod) handler).getMethod().getDeclaredAnnotations())
                .anyMatch(this::isAuthorize);

        if (containsAuthorize) {
            return true;
        }

        response.setStatus(403);

        return false;

    }

    private boolean isAuthorize(Annotation annotation) {
        return annotation instanceof PreAuthorize || annotation instanceof PostAuthorize;
    }

}
