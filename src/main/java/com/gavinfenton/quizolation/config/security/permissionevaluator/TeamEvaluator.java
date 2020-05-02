package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.config.annotation.CastTargetTo;
import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Team;
import org.springframework.stereotype.Component;

@Component
@CastTargetTo(Team.class)
public class TeamEvaluator implements Evaluator<Team> {

    public boolean hasPermission(AppUser appUser, Team targetDomainObject, String permission) {
        return false;
    }

    public boolean hasPermission(AppUser appUser, Long targetId, String permission) {
        return false;
    }

}
