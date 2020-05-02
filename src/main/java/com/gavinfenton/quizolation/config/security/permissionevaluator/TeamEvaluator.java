package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.service.TeamService;
import com.gavinfenton.quizolation.service.TeamUserService;
import org.springframework.stereotype.Component;

@Component
public class TeamEvaluator implements Evaluator<Team> {

    private final TeamService teamService;
    private final TeamUserService teamUserService;

    public TeamEvaluator(TeamService teamService, TeamUserService teamUserService) {
        this.teamService = teamService;
        this.teamUserService = teamUserService;
    }

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
                return true;
            default:
                return false;
        }
    }

    private boolean isTeamOwner(AppUser appUser, Long teamId) {
        return teamService.getTeam(teamId).getLeaderId().equals(appUser.getId());
    }

    private boolean isTeamMember(AppUser appUser, Long teamId) {
        return teamUserService.isQuizTeamMember(teamId, appUser.getId());
    }

}
