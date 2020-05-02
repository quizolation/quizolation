package com.gavinfenton.quizolation.config.security;

import com.gavinfenton.quizolation.config.security.permissionevaluator.Evaluator;
import com.gavinfenton.quizolation.entity.AppUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class QuizPermissionEvaluator implements PermissionEvaluator {

    private final Map<String, Evaluator<?>> permissionEvaluators;

    public QuizPermissionEvaluator(@Qualifier("evaluator") Map<String, Evaluator<?>> permissionEvaluators) {
        this.permissionEvaluators = permissionEvaluators;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return permissionEvaluators.get(targetDomainObject.getClass().getSimpleName())
                .hasPermission(getUser(authentication), targetDomainObject, (String) permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return permissionEvaluators.get(targetType)
                .hasPermission(getUser(authentication), (Long) targetId, (String) permission);
    }

    private AppUser getUser(Authentication authentication) {
        return (AppUser) authentication.getPrincipal();
    }

}
