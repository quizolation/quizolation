package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // TODO: Authorise
    @PostMapping(Endpoints.ROUND + Endpoints.QUESTIONS)
    public ResponseEntity<Question> createQuestion(@PathVariable(Endpoints.ROUND_ID) Long roundId, @RequestBody Question question) {
        Question created = questionService.createQuestion(roundId, question);
        URI location = URI.create(EndpointHelper.insertId(Endpoints.QUESTION, created.getId()));

        return ResponseEntity.created(location).body(created);
    }

    // TODO: Authorise
    @GetMapping(Endpoints.QUESTION)
    public ResponseEntity<Question> getQuestion(@PathVariable(Endpoints.QUESTION_ID) Long questionId) {
        return ResponseEntity.ok(questionService.getQuestion(questionId));
    }

    // TODO: Authorise
    @GetMapping(Endpoints.ROUND + Endpoints.QUESTIONS)
    public ResponseEntity<List<Question>> getQuestions(@PathVariable(Endpoints.ROUND_ID) Long roundId) {
        return ResponseEntity.ok(questionService.getQuestions(roundId));
    }

    // TODO: Authorise
    @PutMapping(Endpoints.QUESTION)
    public ResponseEntity<Question> updateQuestion(@PathVariable(Endpoints.QUESTION_ID) Long questionId, @RequestBody Question question) {
        return ResponseEntity.ok(questionService.updateQuestion(questionId, question));
    }

    // TODO: Authorise
    @DeleteMapping(Endpoints.QUESTION)
    public ResponseEntity<Void> deleteQuestion(@PathVariable(Endpoints.QUESTION_ID) Long questionId) {
        questionService.deleteQuestion(questionId);

        return ResponseEntity.noContent().build();
    }

}
