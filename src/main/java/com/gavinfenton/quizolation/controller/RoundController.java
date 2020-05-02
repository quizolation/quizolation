package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.service.RoundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class RoundController {

    private final RoundService roundService;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    // TODO: Authorise
    @PostMapping(Endpoints.QUIZ + Endpoints.ROUNDS)
    public ResponseEntity<Round> createRound(@PathVariable(Endpoints.QUIZ_ID) Long quizId, @RequestBody Round round) {
        Round created = roundService.createRound(quizId, round);
        URI location = URI.create(EndpointHelper.insertId(Endpoints.ROUND, created.getId()));

        return ResponseEntity.created(location).body(created);
    }

    // TODO: Authorise
    @GetMapping(Endpoints.ROUND)
    public ResponseEntity<Round> getRound(@PathVariable(Endpoints.ROUND_ID) Long roundId) {
        return ResponseEntity.ok(roundService.getRound(roundId));
    }

    // TODO: Authorise
    @GetMapping(Endpoints.QUIZ + Endpoints.ROUNDS)
    public ResponseEntity<List<Round>> getRounds(@PathVariable(Endpoints.QUIZ_ID) Long quizId) {
        return ResponseEntity.ok(roundService.getRounds(quizId));
    }

    // TODO: Authorise
    @PutMapping(Endpoints.ROUND)
    public ResponseEntity<Round> updateRound(@PathVariable(Endpoints.ROUND_ID) Long roundId, @RequestBody Round round) {
        return ResponseEntity.ok(roundService.updateRound(roundId, round));
    }

    // TODO: Authorise
    @DeleteMapping(Endpoints.ROUND)
    public ResponseEntity<Void> deleteRound(@PathVariable(Endpoints.ROUND_ID) Long roundId) {
        roundService.deleteRound(roundId);

        return ResponseEntity.noContent().build();
    }

}
