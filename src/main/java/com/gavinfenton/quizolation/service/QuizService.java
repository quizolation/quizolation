package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.dto.QuizDetailsDTO;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.helper.SecurityHelper;
import com.gavinfenton.quizolation.mapper.QuizDetailsMapper;
import com.gavinfenton.quizolation.repository.QuizRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizDetailsMapper quizDetailsMapper = QuizDetailsMapper.INSTANCE;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz(Quiz quiz) {
        quiz.setId(null);
        quiz.setMasterId(SecurityHelper.getUserId());

        return quizRepository.save(quiz);
    }

    public Quiz createQuiz(QuizDetailsDTO quizDetails) {
        return createQuiz(quizDetailsMapper.toQuiz(quizDetails));
    }

    public Quiz getQuiz(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Quiz"));
    }

    public List<Quiz> getQuizzes() {
        ;
        return quizRepository.findAll();
    }

    public Quiz updateQuiz(Long id, Quiz quiz) {
        Quiz existing = getQuiz(id);
        existing.setName(quiz.getName());

        return quizRepository.save(existing);
    }

    public Quiz updateQuiz(Long id, QuizDetailsDTO quizDetails) {
        return updateQuiz(id, quizDetailsMapper.toQuiz(quizDetails));
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

}
