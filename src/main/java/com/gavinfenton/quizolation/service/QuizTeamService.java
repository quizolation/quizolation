package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.QuizTeam;
import com.gavinfenton.quizolation.repository.QuizTeamRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizTeamService {

    private final QuizTeamRepository quizTeamRepository;

    public QuizTeamService(QuizTeamRepository quizTeamRepository) {
        this.quizTeamRepository = quizTeamRepository;
    }

    public void addTeamToQuiz(Long quizId, Long teamId) {
        QuizTeam quizTeam = new QuizTeam();
        quizTeam.setQuizId(quizId);
        quizTeam.setTeamId(teamId);

        quizTeamRepository.save(quizTeam);
    }
}
