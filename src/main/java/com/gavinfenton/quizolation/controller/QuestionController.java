package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Creates a question for a round.
     * <p>
     * Permissions: User must be the quiz master of the quiz round that is getting added to.
     *
     * @param roundId  ID of round to add the question to.
     * @param question Question to create.
     * @return Question round.
     */
    @PreAuthorize("hasPermission(#roundId, 'Round', 'UPDATE')")
    @PostMapping(Endpoints.ROUND + Endpoints.QUESTIONS)
    public ResponseEntity<Question> createQuestion(@PathVariable(Endpoints.ROUND_ID) Long roundId, @RequestBody Question question) {
        Question created = questionService.createQuestion(roundId, question);
        URI location = URI.create(EndpointHelper.insertId(Endpoints.QUESTION, created.getId()));

        return ResponseEntity.created(location).body(created);
    }

    /**
     * Gets question details by ID.
     * <p>
     * Permissions: Quiz masters and members of quiz teams should be able to view the questions for it.
     *
     * @param questionId ID of the question to return details for.
     * @return Question details.
     */
    @PreAuthorize("hasPermission(#questionId, 'QUESTION', 'READ')")
    @GetMapping(Endpoints.QUESTION)
    public ResponseEntity<Question> getQuestion(@PathVariable(Endpoints.QUESTION_ID) Long questionId) {
        return ResponseEntity.ok(questionService.getQuestion(questionId));
    }

    /**
     * Get a list of questions of a round.
     * <p>
     * Permissions: Quiz masters and members of quiz teams should be able to view the questions for it.
     *
     * @param roundId ID of the round to return questions for.
     * @return List of round questions.
     */
    @PreAuthorize("hasPermission(#roundId, 'Round', 'READ')")
    @GetMapping(Endpoints.ROUND + Endpoints.QUESTIONS)
    public ResponseEntity<List<Question>> getQuestions(@PathVariable(Endpoints.ROUND_ID) Long roundId) {
        return ResponseEntity.ok(questionService.getQuestions(roundId));
    }

    /**
     * Updates an existing question.
     * <p>
     * Permissions: Only the quiz master should be able to update its questions.
     *
     * @param questionId ID of the question to update.
     * @param question   Question details to update.
     * @return Updated question details.
     */
    @PreAuthorize("hasPermission(#questionId, 'Question', 'UPDATE')")
    @PutMapping(Endpoints.QUESTION)
    public ResponseEntity<Question> updateQuestion(@PathVariable(Endpoints.QUESTION_ID) Long questionId, @RequestBody Question question) {
        return ResponseEntity.ok(questionService.updateQuestion(questionId, question));
    }

    /**
     * Deletes an existing question.
     * <p>
     * Permissions: Only the quiz master should be able to delete its questions.
     *
     * @param questionId ID of the question to delete.
     */
    @PreAuthorize("hasPermission(#questionId, 'Question', 'DELETE')")
    @DeleteMapping(Endpoints.QUESTION)
    public ResponseEntity<Void> deleteQuestion(@PathVariable(Endpoints.QUESTION_ID) Long questionId) {
        questionService.deleteQuestion(questionId);

        return ResponseEntity.noContent().build();
    }

}
