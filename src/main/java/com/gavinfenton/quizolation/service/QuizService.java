package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.repository.QuizRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz(Quiz quiz) {
        quiz.setId(null);

        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Quiz"));
    }

    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz updateQuiz(Long id, Quiz quiz) {
        quiz.setId(id);

        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

}
