package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping(Endpoints.TEAMS)
    public ResponseEntity<?> createTeam(@RequestBody Team team) {
        return new ResponseEntity<>(teamService.createTeam(team), HttpStatus.CREATED);
    }

    @GetMapping(Endpoints.TEAMS)
    public ResponseEntity<?> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping(Endpoints.TEAM)
    public ResponseEntity<?> getTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId) {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    @PutMapping(Endpoints.TEAM)
    public ResponseEntity<?> updateTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId, @RequestBody Team team) {
        return new ResponseEntity<>(teamService.updateTeam(teamId, team), HttpStatus.OK);
    }

    @DeleteMapping(Endpoints.TEAM)
    public void deleteTeam(@PathVariable(Endpoints.TEAM_ID) Long teamId) {
        teamService.deleteTeam(teamId);
    }

}
