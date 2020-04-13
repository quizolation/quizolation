package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.repository.QuizRepository;
import com.gavinfenton.quizolation.repository.TeamRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final TeamRepository teamRepository;

    public QuizService(QuizRepository quizRepository, TeamRepository teamRepository) {
        this.quizRepository = quizRepository;
        this.teamRepository = teamRepository;
    }

    public Quiz createQuiz(Quiz quiz) {
        quiz.setId(null);

        return quizRepository.save(quiz);
    }

    public Quiz addTeamToQuiz(Long quizId, Long teamId) {
        //Find that both team and quiz already exist
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ObjectNotFoundException(teamId, "Team"));
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ObjectNotFoundException(quizId, "Quiz"));

        //Check that team isn't already related to quiz
        //List<Quiz> quizForTeamRelationship = quizRepository.findByTeamsContaining(team);

        List<Team> teams = new ArrayList<>();
        teams.add(team);

        quiz.setTeams(teams);
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
