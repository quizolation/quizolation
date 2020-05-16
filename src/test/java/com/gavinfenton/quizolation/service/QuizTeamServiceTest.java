package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.QuizTeam;
import com.gavinfenton.quizolation.repository.QuizTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class QuizTeamServiceTest {

    @InjectMocks
    private QuizTeamService quizTeamService;

    @Mock
    private QuizTeamRepository quizTeamRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testAddUserToTeamCreatesEntryAndSavesToRepository() {
        // Given
        QuizTeam quizTeam = new QuizTeam();
        quizTeam.setQuizId(321L);
        quizTeam.setTeamId(123L);

        // When
        quizTeamService.addTeamToQuiz(quizTeam.getQuizId(), quizTeam.getTeamId());

        // Then
        verify(quizTeamRepository).save(quizTeam);
    }

}
