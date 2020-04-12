package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.service.QuizService;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

=======
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
>>>>>>> origin/master
import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(Endpoints.QUIZZES)
<<<<<<< HEAD
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        return new ResponseEntity<>(quizService.createQuiz(quiz), HttpStatus.CREATED);
    }

    @GetMapping(Endpoints.QUIZ)
    public ResponseEntity<Quiz> getQuiz(@PathVariable("quizId") Long quizId) {
=======
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz, HttpServletRequest request) {
        Quiz created = quizService.createQuiz(quiz);

        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + created.getId())).body(created);
    }

    @GetMapping(Endpoints.QUIZ)
    public ResponseEntity<Quiz> getQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId) {
>>>>>>> origin/master
        return ResponseEntity.ok(quizService.getQuiz(quizId));
    }

    @GetMapping(Endpoints.QUIZZES)
<<<<<<< HEAD
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
=======
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
>>>>>>> origin/master
    }

}
