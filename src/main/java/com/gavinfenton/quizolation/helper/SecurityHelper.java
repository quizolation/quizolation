package com.gavinfenton.quizolation.helper;

import com.gavinfenton.quizolation.entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

    public static AppUser getUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getUserId() {
        return getUser().getId();
    }

}
