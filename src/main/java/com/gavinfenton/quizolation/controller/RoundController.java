package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.RoundDTO;
import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.mapper.RoundMapper;
import com.gavinfenton.quizolation.service.RoundService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class RoundController {

    private final RoundService roundService;
    private final RoundMapper roundMapper = RoundMapper.INSTANCE;

    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    /**
     * Creates a round for a quiz.
     * <p>
     * Permissions: User must be the quiz master of the quiz that is getting added to.
     *
     * @param quizId ID of quiz to add the round to.
     * @param round  Round to create.
     * @return Created round.
     */
    @PreAuthorize("hasPermission(#quizId, 'Quiz', 'UPDATE')")
    @PostMapping(Endpoints.QUIZ + Endpoints.ROUNDS)
    public ResponseEntity<RoundDTO> createRound(@PathVariable(Endpoints.QUIZ_ID) Long quizId, @RequestBody RoundDTO round) {
        Round created = roundService.createRound(quizId, roundMapper.toRound(round));
        URI location = URI.create(EndpointHelper.insertId(Endpoints.ROUND, created.getId()));

        return ResponseEntity.created(location).body(roundMapper.toDTO(created));
    }

    /**
     * Gets round details by ID.
     * <p>
     * Permissions: Quiz masters and members of quiz teams should be able to view the rounds for it.
     *
     * @param roundId ID of the round to return details for.
     * @return Round details.
     */
    @PreAuthorize("hasPermission(#roundId, 'Round', 'READ')")
    @GetMapping(Endpoints.ROUND)
    public ResponseEntity<RoundDTO> getRound(@PathVariable(Endpoints.ROUND_ID) Long roundId) {
        return ResponseEntity.ok(roundMapper.toDTO(roundService.getRound(roundId)));
    }

    /**
     * Get a list of rounds of a quiz.
     * <p>
     * Permissions: Quiz masters and members of quiz teams should be able to view the rounds for it.
     *
     * @param quizId ID of the quiz to return rounds for.
     * @return List of quiz rounds.
     */
    @PreAuthorize("hasPermission(#quizId, 'Quiz', 'READ')")
    @GetMapping(Endpoints.QUIZ + Endpoints.ROUNDS)
    public ResponseEntity<List<RoundDTO>> getRounds(@PathVariable(Endpoints.QUIZ_ID) Long quizId) {
        return ResponseEntity.ok(roundMapper.toDTOList(roundService.getRounds(quizId)));
    }

    /**
     * Updates an existing round.
     * <p>
     * Permissions: Only the quiz master should be able to update its rounds.
     *
     * @param roundId ID of the round to update.
     * @param round   Round details to update.
     * @return Updated round details.
     */
    @PreAuthorize("hasPermission(#roundId, 'Round', 'UPDATE')")
    @PutMapping(Endpoints.ROUND)
    public ResponseEntity<RoundDTO> updateRound(@PathVariable(Endpoints.ROUND_ID) Long roundId, @RequestBody RoundDTO round) {
        return ResponseEntity.ok(roundMapper.toDTO(roundService.updateRound(roundId, roundMapper.toRound(round))));
    }

    /**
     * Deletes an existing round.
     * <p>
     * Permissions: Only the quiz master should be able to delete its rounds.
     *
     * @param roundId ID of the round to delete.
     */
    @PreAuthorize("hasPermission(#roundId, 'Round', 'DELETE')")
    @DeleteMapping(Endpoints.ROUND)
    public ResponseEntity<Void> deleteRound(@PathVariable(Endpoints.ROUND_ID) Long roundId) {
        roundService.deleteRound(roundId);

        return ResponseEntity.noContent().build();
    }

}
