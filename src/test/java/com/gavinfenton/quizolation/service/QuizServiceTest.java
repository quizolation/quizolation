package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.repository.QuizRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private TeamService teamService;

    @Mock
    private QuizRepository quizRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateQuizSavesToAndReturnsFromRepository() {
        // Given
        Quiz quizSaving = new Quiz();
        quizSaving.setName("Some Quiz");
        Quiz quizExpected = new Quiz();
        given(quizRepository.save(quizSaving)).willReturn(quizExpected);

        // When
        Quiz quizActual = quizService.createQuiz(quizSaving);

        // Then
        verify(quizRepository).save(quizSaving);
        assertEquals(quizExpected, quizActual);
    }

    @Test
    public void testCreateQuizNullsIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Quiz quizSaving = new Quiz();
        quizSaving.setId(123L);
        quizSaving.setName("Some Quiz");
        Quiz quizExpected = new Quiz();
        given(quizRepository.save(quizSaving)).willReturn(quizExpected);

        // When
        Quiz quizActual = quizService.createQuiz(quizSaving);

        // Then
        assertNull(quizSaving.getId());
        verify(quizRepository).save(quizSaving);
        assertEquals(quizExpected, quizActual);
    }

    @Test
    public void testGetQuizReturnsExistingQuizFromRepository() {
        // Given
        Quiz quizExpected = new Quiz();
        Long idExpected = 321L;
        quizExpected.setName("Some Quiz");
        given(quizRepository.findById(idExpected)).willReturn(Optional.of(quizExpected));

        // When
        Quiz quizActual = quizService.getQuiz(idExpected);

        // Then
        verify(quizRepository).findById(idExpected);
        assertEquals(quizExpected, quizActual);
    }

    @Test
    public void testGetQuizThrowsExceptionForNonExistingQuiz() {
        // Given
        Long idExpected = 321L;
        given(quizRepository.findById(idExpected)).willReturn(Optional.empty());

        // When / Then
        assertThrows(ObjectNotFoundException.class, () -> quizService.getQuiz(idExpected));
    }

    @Test
    public void testGetQuizzesReturnsExistingQuizzesFromRepository() {
        // Given
        Quiz quizExpected1 = new Quiz();
        Quiz quizExpected2 = new Quiz();
        quizExpected1.setName("Quiz 1");
        quizExpected2.setName("Quiz 2");
        List<Quiz> quizzesExpected = Arrays.asList(quizExpected1, quizExpected2);
        given(quizRepository.findAll()).willReturn(quizzesExpected);

        // When
        List<Quiz> quizzesActual = quizService.getQuizzes();

        // Then
        verify(quizRepository).findAll();
        assertEquals(quizzesExpected, quizzesActual);
    }

    @Test
    public void testUpdateQuizSavesToAndReturnsFromRepository() {
        // Given
        Quiz quizSaving = new Quiz();
        quizSaving.setName("Some Quiz");
        Quiz quizExpected = new Quiz();
        given(quizRepository.save(quizSaving)).willReturn(quizExpected);

        // When
        Quiz quizActual = quizService.updateQuiz(321L, quizSaving);

        // Then
        verify(quizRepository).save(quizSaving);
        assertEquals(quizExpected, quizActual);
    }

    @Test
    public void testUpdateQuizSetsIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Quiz quizSaving = new Quiz();
        quizSaving.setId(123L);
        quizSaving.setName("Some Quiz");
        Quiz quizExpected = new Quiz();
        Long idExpected = 321L;
        quizExpected.setId(idExpected);
        given(quizRepository.save(quizSaving)).willReturn(quizExpected);

        // When
        Quiz quizActual = quizService.updateQuiz(idExpected, quizSaving);

        // Then
        assertEquals(idExpected, quizSaving.getId());
        verify(quizRepository).save(quizSaving);
        assertEquals(quizExpected, quizActual);
    }

    @Test
    public void testAddTeamToQuizCallsAndReturnsFromRepo() {
        fail();
//        //Given
//        Quiz quizSaving = new Quiz();
//        quizSaving.setId(32L);
//        Long quizIdSaving = quizSaving.getId();
//        Quiz quizExpected = new Quiz();
//        quizExpected.setId(23L);
//        Long teamIdSaving = 1L;
//        Team teamExpected = new Team();
//        given(teamService.getTeam(teamIdSaving)).willReturn(teamExpected);
//        given(quizRepository.findById(quizIdSaving)).willReturn(Optional.of(quizSaving));
//        given(quizRepository.save(quizSaving)).willReturn(quizExpected);
//
//        //When
//        Quiz quizActual = quizService.addTeamToQuiz(quizIdSaving, teamIdSaving);
//
//        //Then
//        verify(teamService).getTeam(teamIdSaving);
//        verify(quizRepository).findById(quizIdSaving);
//        verify(quizRepository).save(quizSaving);
//        assertEquals(quizExpected, quizActual);
//        assertEquals(teamExpected,quizSaving.getTeams().get(0));
//        assertEquals(1, quizSaving.getTeams().size());

    }

    @Test
    public void testDeleteQuizCallsDeleteOnRepository() {
        // Given
        Long idDeleting = 321L;

        // When
        quizService.deleteQuiz(idDeleting);

        // Then
        verify(quizRepository).deleteById(idDeleting);
    }

}
