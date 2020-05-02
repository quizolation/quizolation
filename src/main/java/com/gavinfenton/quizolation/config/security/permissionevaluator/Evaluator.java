package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.BaseEntity;

public interface Evaluator<T extends BaseEntity> {

    boolean hasPermission(AppUser appUser, T targetDomainObject, String permission);

    boolean hasPermission(AppUser appUser, Long targetId, String permission);

}
