package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Creates a new team.
     * <p>
     * Permissions: All authenticated users are permitted to create teams.
     *
     * @param team team to create.
     * @return Created team.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping(Endpoints.TEAMS)
    public ResponseEntity<Team> createTeam(@RequestBody Team team, HttpServletRequest request) {
        Team createdTeam = teamService.createTeam(team);
        URI location = URI.create(EndpointHelper.insertId(Endpoints.TEAM, createdTeam.getId()));

        return ResponseEntity.created(location).body(createdTeam);
    }

    /**
     * Gets a list of teams.
     * <p>
     * Permissions: All authenticated users can see all teams.
     *
     * @return List of teams.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping(Endpoints.TEAMS)
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    /**
     * Gets team details by ID.
     * <p>
     * Permissions: All authenticated users can see all teams.
     *
     * @param teamId ID of team to return details for.
     * @return Team details.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping(Endpoints.TEAM)
    public ResponseEntity<Team> getTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId) {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    /**
     * Updates an existing quiz.
     * <p>
     * Permissions: Only the quiz master should be able to update.
     *
     * @param teamId ID of the team to update.
     * @param team   Team details to update.
     * @return Updated team details.
     */
    @PreAuthorize("hasPermission(#teamId, 'Team', 'UPDATE')")
    @PutMapping(Endpoints.TEAM)
    public ResponseEntity<Team> updateTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId, @RequestBody Team team) {
        return new ResponseEntity<>(teamService.updateTeam(teamId, team), HttpStatus.OK);
    }

    /**
     * Deletes an existing team.
     * <p>
     * Permissions: Only the team should be able to delete.
     *
     * @param teamId ID of the quiz to delete.
     */
    @PreAuthorize("hasPermission(#teamId, 'Team', 'DELETE')")
    @DeleteMapping(Endpoints.TEAM)
    public ResponseEntity<Void> deleteTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId) {
        teamService.deleteTeam(teamId);

        return ResponseEntity.noContent().build();
    }

}
