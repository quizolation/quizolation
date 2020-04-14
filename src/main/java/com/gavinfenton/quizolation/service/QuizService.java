package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.repository.QuizRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final TeamService teamService;

    public QuizService(QuizRepository quizRepository, TeamService teamService) {
        this.quizRepository = quizRepository;
        this.teamService = teamService;
    }

    public Quiz createQuiz(Quiz quiz) {
        quiz.setId(null);

        return quizRepository.save(quiz);
    }

    public Quiz addTeamToQuiz(Long quizId, Long teamId) {
        //Find that both team and quiz already exist
        Team team = teamService.getTeam(teamId);
        Quiz quiz = getQuiz(quizId);

        quiz.getTeams().add(team);

        quizRepository.save(quiz);
        return quiz;
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
