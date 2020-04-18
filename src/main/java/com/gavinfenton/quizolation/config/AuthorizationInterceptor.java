package com.gavinfenton.quizolation.config;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws NoSuchMethodException {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        final Method method = ((HandlerMethod) handler).getMethod();

        if (method.equals(BasicErrorController.class.getMethod("error", HttpServletRequest.class))) {
            return true;
        }

        boolean containsAuthorize = Arrays
                .stream(method.getDeclaredAnnotations())
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
