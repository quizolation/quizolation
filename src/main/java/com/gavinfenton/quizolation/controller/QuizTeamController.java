package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.service.QuizTeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizTeamController {

    private final QuizTeamService quizTeamService;

    public QuizTeamController(QuizTeamService quizTeamService) {
        this.quizTeamService = quizTeamService;
    }

    @PreAuthorize("hasPermission(#quizId, 'Quiz', 'UPDATE')")
    @PostMapping(Endpoints.QUIZ + Endpoints.TEAM)
    public ResponseEntity<Void> addTeamToQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId, @PathVariable(Endpoints.TEAM_ID) Long teamId) {
        quizTeamService.addTeamToQuiz(quizId, teamId);

        return ResponseEntity.noContent().build();
    }

}
