package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.QuestionAndAnswerDTO;
import com.gavinfenton.quizolation.dto.QuestionDTO;
import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.helper.EndpointHelper;
import com.gavinfenton.quizolation.helper.SecurityHelper;
import com.gavinfenton.quizolation.mapper.QuestionAndAnswerMapper;
import com.gavinfenton.quizolation.mapper.QuestionMapper;
import com.gavinfenton.quizolation.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper = QuestionMapper.INSTANCE;
    private final QuestionAndAnswerMapper questionAndAnswerMapper = QuestionAndAnswerMapper.INSTANCE;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
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
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable(Endpoints.QUESTION_ID) Long questionId) {
        return ResponseEntity.ok(map(questionService.getQuestion(questionId)));
    }

    /**
     * Creates a question for a round.
     * <p>
     * Permissions: User must be the quiz master of the quiz round that is getting added to.
     *
     * @param roundId           ID of round to add the question to.
     * @param questionAndAnswer Question to create.
     * @return Question round.
     */
    @PreAuthorize("hasPermission(#roundId, 'Round', 'UPDATE')")
    @PostMapping(Endpoints.ROUND + Endpoints.QUESTIONS)
    public ResponseEntity<QuestionDTO> createQuestion(@PathVariable(Endpoints.ROUND_ID) Long roundId, @RequestBody QuestionAndAnswerDTO questionAndAnswer) {
        Question created = questionService.createQuestion(roundId, questionAndAnswerMapper.toQuestion(questionAndAnswer));
        URI location = URI.create(EndpointHelper.insertId(Endpoints.QUESTION, created.getId()));

        return ResponseEntity.created(location).body(questionAndAnswerMapper.toDTO(created));
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
    public ResponseEntity<List<?>> getQuestions(@PathVariable(Endpoints.ROUND_ID) Long roundId) {
        return ResponseEntity.ok(map(questionService.getQuestions(roundId)));
    }

    /**
     * Updates an existing question.
     * <p>
     * Permissions: Only the quiz master should be able to update its questions.
     *
     * @param questionId        ID of the question to update.
     * @param questionAndAnswer Question details to update.
     * @return Updated question details.
     */
    @PreAuthorize("hasPermission(#questionId, 'Question', 'UPDATE')")
    @PutMapping(Endpoints.QUESTION)
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable(Endpoints.QUESTION_ID) Long questionId, @RequestBody QuestionAndAnswerDTO questionAndAnswer) {
        return ResponseEntity.ok(questionAndAnswerMapper.toDTO(questionService.updateQuestion(questionId, questionAndAnswerMapper.toQuestion(questionAndAnswer))));
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

    private QuestionDTO map(Question question) {
        return questionService.isTeamMemberOfRelatedQuiz(question.getId(), SecurityHelper.getUserId())
                ? questionAndAnswerMapper.toDTO(question)
                : questionMapper.toDTO(question);
    }

    private List<?> map(List<Question> question) {
        return questionService.isTeamMemberOfRelatedQuiz(question.get(0).getId(), SecurityHelper.getUserId())
                ? questionAndAnswerMapper.toDTOList(question)
                : questionMapper.toDTOList(question);
    }

}
