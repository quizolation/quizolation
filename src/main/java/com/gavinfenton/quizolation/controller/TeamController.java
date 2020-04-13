package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(Endpoints.TEAMS)
    public ResponseEntity<Team> createTeam(@RequestBody Team team, HttpServletRequest request) {
        Team createdTeam = teamService.createTeam(team);

        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + createdTeam.getId())).body(createdTeam);
    }

    @GetMapping(Endpoints.TEAMS)
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping(Endpoints.TEAM)
    public ResponseEntity<Team> getTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId) {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    @PutMapping(Endpoints.TEAM)
    public ResponseEntity<Team> updateTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId, @RequestBody Team team) {
        return new ResponseEntity<>(teamService.updateTeam(teamId, team), HttpStatus.OK);
    }

    @DeleteMapping(Endpoints.TEAM)
    public ResponseEntity<Void> deleteTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }

}
