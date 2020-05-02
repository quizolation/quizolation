package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.BaseEntity;

public interface Evaluator<T extends BaseEntity> {

    default boolean hasPermission(AppUser appUser, Object targetDomainObject, String permission) {
        return hasPermission(appUser, (T) targetDomainObject, permission);
    }

    boolean hasPermission(AppUser appUser, T targetDomainObject, String permission);

    boolean hasPermission(AppUser appUser, Long targetId, String permission);

}
