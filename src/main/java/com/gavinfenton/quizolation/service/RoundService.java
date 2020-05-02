package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.repository.RoundRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundService {

    private final RoundRepository roundRepository;

    private final QuizService quizService;

    public RoundService(RoundRepository roundRepository, QuizService quizService) {
        this.roundRepository = roundRepository;
        this.quizService = quizService;
    }

    public Round createRound(Long quizId, Round round) {
        round.setId(null);
        round.setQuizId(quizId);

        return roundRepository.save(round);
    }

    public Round getRound(Long id) {
        return roundRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Round"));
    }

    public List<Round> getRounds(Long quizId) {
        // Ensure Quiz exists
        quizService.getQuiz(quizId);

        return roundRepository.findAllByQuizId(quizId);
    }

    public Round updateRound(Long id, Round round) {
        round.setId(id);

        return roundRepository.save(round);
    }

    public void deleteRound(Long id) {
        roundRepository.deleteById(id);
    }

    public boolean isMasterOfRelatedQuiz(Long roundId, Long userId) {
        return roundRepository.isMasterOfRelatedQuiz(roundId, userId);
    }

    public boolean isTeamMemberOfRelatedQuiz(Long roundId, Long userId) {
        return roundRepository.isTeamMemberOfRelatedQuiz(roundId, userId);
    }

}
