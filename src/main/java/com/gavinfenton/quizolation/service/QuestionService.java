package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.repository.QuestionRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final RoundService roundService;

    public QuestionService(QuestionRepository questionRepository, RoundService roundService) {
        this.questionRepository = questionRepository;
        this.roundService = roundService;
    }

    public Question createQuestion(Long roundId, Question question) {
        question.setId(null);
        question.setRoundId(roundId);

        return questionRepository.save(question);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Question"));
    }

    public List<Question> getQuestions(Long roundId) {
        // Ensure Round exists
        roundService.getRound(roundId);

        return questionRepository.findAllByRoundId(roundId);
    }

    public Question updateQuestion(Long id, Question question) {
        question.setId(id);

        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public boolean isMasterOfRelatedQuiz(Long questionId, Long userId) {
        return questionRepository.isMasterOfRelatedQuiz(questionId, userId);
    }

    public boolean isTeamMemberOfRelatedQuiz(Long questionId, Long userId) {
        return questionRepository.isTeamMemberOfRelatedQuiz(questionId, userId);
    }

}
