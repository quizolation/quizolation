package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(Endpoints.QUIZZES)
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        return new ResponseEntity<>(quizService.createQuiz(quiz), HttpStatus.CREATED);
    }

    @GetMapping(Endpoints.QUIZ)
    public ResponseEntity<Quiz> getQuiz(@PathVariable("quizId") Long quizId) {
        return ResponseEntity.ok(quizService.getQuiz(quizId));
    }

    @GetMapping(Endpoints.QUIZZES)
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @PutMapping(Endpoints.QUIZ)
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz, @PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.updateQuiz(quiz, quizId));
    }

    @DeleteMapping(Endpoints.QUIZ)
    public ResponseEntity<?> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);

        return ResponseEntity.ok().build();
    }

}
