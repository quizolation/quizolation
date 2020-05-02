package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Creates a new quiz.
     * <p>
     * Permissions: All authenticated users are permitted to create quizzes.
     *
     * @param quiz Quiz to create.
     * @return Created quiz.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping(Endpoints.QUIZZES)
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz created = quizService.createQuiz(quiz);
        URI location = URI.create(EndpointHelper.insertId(Endpoints.QUIZ, created.getId()));

        return ResponseEntity.created(location).body(created);
    }

    /**
     * Gets quiz details by ID.
     * <p>
     * Permissions: Quiz masters and members of quiz teams should be able to view the quiz.
     *
     * @param quizId ID of quiz to return details for.
     * @return Quiz details.
     */
    @PreAuthorize("hasPermission(#quizId, 'Quiz', 'READ')")
    @GetMapping(Endpoints.QUIZ)
    public ResponseEntity<Quiz> getQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId) {
        return ResponseEntity.ok(quizService.getQuiz(quizId));
    }

    /**
     * Get a list of quizzes available to the user.
     * <p>
     * Permissions: Quizzes should be filters to those that the user is a master or team member for.
     *
     * @return List of quizzes.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping(Endpoints.QUIZZES)
    public ResponseEntity<List<Quiz>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    /**
     * Updates an existing quiz.
     * <p>
     * Permissions: Only the quiz master should be able to update.
     *
     * @param quizId ID of the quiz to update.
     * @param quiz   Quiz details to update.
     * @return Updated quiz details
     */
    @PreAuthorize("hasPermission(#quizId, 'Quiz', 'UPDATE')")
    @PutMapping(Endpoints.QUIZ)
    public ResponseEntity<Quiz> updateQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId, @RequestBody Quiz quiz) {
        return ResponseEntity.ok(quizService.updateQuiz(quizId, quiz));
    }

    /**
     * Deletes an existing quiz.
     * <p>
     * Permissions: Only the quiz master should be able to delete.
     *
     * @param quizId ID of the quiz to delete.
     */
    @PreAuthorize("hasPermission(#quizId, 'Quiz', 'DELETE')")
    @DeleteMapping(Endpoints.QUIZ)
    public ResponseEntity<Void> deleteQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId) {
        quizService.deleteQuiz(quizId);

        return ResponseEntity.noContent().build();
    }

}
