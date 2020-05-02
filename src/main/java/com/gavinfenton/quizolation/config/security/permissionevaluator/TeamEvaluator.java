package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamEvaluator implements Evaluator<Team> {

    public boolean hasPermission(AppUser appUser, Team team, String permission) {
        return hasPermission(appUser, team.getId(), permission);
    }

    public boolean hasPermission(AppUser appUser, Long teamId, String permission) {
        return false;
    }

}
