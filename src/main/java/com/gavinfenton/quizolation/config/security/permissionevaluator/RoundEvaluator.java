package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Round;
import org.springframework.stereotype.Component;

@Component
public class RoundEvaluator implements Evaluator<Round> {

    public boolean hasPermission(AppUser appUser, Round Round, String permission) {
        return hasPermission(appUser, Round.getId(), permission);
    }

    public boolean hasPermission(AppUser appUser, Long RoundId, String permission) {
        return false;
    }

}
