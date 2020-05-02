package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.QuizDetailsDTO;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.mapper.QuizDetailsMapper;
import com.gavinfenton.quizolation.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;
    private final QuizDetailsMapper quizDetailsMapper = QuizDetailsMapper.INSTANCE;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Creates a new quiz.
     * <p>
     * Permissions: All authenticated users are permitted to create quizzes.
     *
     * @param quizDetails Quiz to create.
     * @return Created quiz.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping(Endpoints.QUIZZES)
    public ResponseEntity<QuizDetailsDTO> createQuiz(@RequestBody QuizDetailsDTO quizDetails) {
        Quiz created = quizService.createQuiz(quizDetails);
        URI location = URI.create(EndpointHelper.insertId(Endpoints.QUIZ, created.getId()));

        return ResponseEntity.created(location).body(quizDetailsMapper.toDTO(created));
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
    public ResponseEntity<QuizDetailsDTO> getQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId) {
        return ResponseEntity.ok(quizDetailsMapper.toDTO(quizService.getQuiz(quizId)));
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
    public ResponseEntity<List<QuizDetailsDTO>> getQuizzes() {
        return ResponseEntity.ok(quizDetailsMapper.toDTOList(quizService.getQuizzes()));
    }

    /**
     * Updates an existing quiz.
     * <p>
     * Permissions: Only the quiz master should be able to update.
     *
     * @param quizId      ID of the quiz to update.
     * @param quizDetails Quiz details to update.
     * @return Updated quiz details.
     */
    @PreAuthorize("hasPermission(#quizId, 'Quiz', 'UPDATE')")
    @PutMapping(Endpoints.QUIZ)
    public ResponseEntity<QuizDetailsDTO> updateQuiz(@PathVariable(Endpoints.QUIZ_ID) Long quizId, @RequestBody QuizDetailsDTO quizDetails) {
        return ResponseEntity.ok(quizDetailsMapper.toDTO(quizService.updateQuiz(quizId, quizDetails)));
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
