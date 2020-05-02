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

// TODO: Need to think about who can read teams, e.g. all authenitcated or only team members
@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(Endpoints.TEAMS)
    public ResponseEntity<Team> createTeam(@RequestBody Team team, HttpServletRequest request) {
        Team createdTeam = teamService.createTeam(team);
        URI location = URI.create(EndpointHelper.insertId(Endpoints.TEAM, createdTeam.getId()));

        return ResponseEntity.created(location).body(createdTeam);
    }

    // TODO: Authorise
    @GetMapping(Endpoints.TEAMS)
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    // TODO: Authorise
    @GetMapping(Endpoints.TEAM)
    public ResponseEntity<Team> getTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId) {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    // TODO: Authorise
    @PutMapping(Endpoints.TEAM)
    public ResponseEntity<Team> updateTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId, @RequestBody Team team) {
        return new ResponseEntity<>(teamService.updateTeam(teamId, team), HttpStatus.OK);
    }

    // TODO: Authorise
    @DeleteMapping(Endpoints.TEAM)
    public ResponseEntity<Void> deleteTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }

}
