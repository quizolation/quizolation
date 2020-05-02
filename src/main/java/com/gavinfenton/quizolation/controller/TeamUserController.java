package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.service.TeamUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamUserController {

    private final TeamUserService teamUserService;

    public TeamUserController(TeamUserService teamUserService) {
        this.teamUserService = teamUserService;
    }

    /**
     * Adds a user to a team.
     * <p>
     * Permissions: User must be the team leader of the team they are adding the user to.
     *
     * @param teamId ID of the team to add the user to.
     * @param userId ID of the user to be added.
     */
    @PreAuthorize("hasPermission(#teamId, 'Team', 'UPDATE')")
    @PostMapping(Endpoints.TEAM + Endpoints.USER)
    public ResponseEntity<Void> addTeamToQuiz(@PathVariable(Endpoints.TEAM_ID) Long teamId, @PathVariable(Endpoints.USER_ID) Long userId) {
        teamUserService.addUserToTeam(teamId, userId);

        return ResponseEntity.noContent().build();
    }

}
