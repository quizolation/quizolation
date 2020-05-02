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
        switch (permission) {
            case "CREATE":
            case "UPDATE":
            case "DELETE":
                return isTeamOwner(appUser, teamId);
            case "READ":
                return isTeamOwner(appUser, teamId) || isTeamMember(appUser, teamId);
            default:
                return false;
        }
    }

    private boolean isTeamOwner(AppUser appUser, Long teamId) {
        // TODO: implement
        return false;
    }

    private boolean isTeamMember(AppUser appUser, Long teamId) {
        // TODO: implement
        return false;
    }

}
