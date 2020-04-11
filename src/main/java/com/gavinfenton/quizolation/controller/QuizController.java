package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        return new ResponseEntity<>(quizService.createQuiz(quiz), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable("id") Long id) {
        return ResponseEntity.ok(quizService.getQuiz(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @PutMapping("{id}")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz, @PathVariable("id") Long id) {
        return ResponseEntity.ok(quizService.updateQuiz(quiz, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id") Long id) {
        quizService.deleteQuiz(id);

        return ResponseEntity.ok().build();
    }

}
