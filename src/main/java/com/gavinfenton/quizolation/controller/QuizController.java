package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(Endpoints.QUIZZES)
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz created = quizService.createQuiz(quiz);
        URI location = URI.create(EndpointHelper.insertIds(Endpoints.QUIZ, created.getId()));

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping(Endpoints.QUIZ)
    public ResponseEntity<Quiz> getQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId) {
        return ResponseEntity.ok(quizService.getQuiz(quizId));
    }

    @GetMapping(Endpoints.QUIZZES)
    public ResponseEntity<List<Quiz>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    @PutMapping(Endpoints.QUIZ)
    public ResponseEntity<Quiz> updateQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId, @RequestBody Quiz quiz) {
        return ResponseEntity.ok(quizService.updateQuiz(quizId, quiz));
    }

    @DeleteMapping(Endpoints.QUIZ)
    public ResponseEntity<Void> deleteQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId) {
        quizService.deleteQuiz(quizId);

        return ResponseEntity.noContent().build();
    }

}
