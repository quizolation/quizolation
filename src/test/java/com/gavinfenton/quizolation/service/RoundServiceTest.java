package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.repository.RoundRepository;
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

public class RoundServiceTest {

    @InjectMocks
    private RoundService roundService;

    @Mock
    private RoundRepository roundRepository;

    @Mock
    private QuizService quizService;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateRoundSavesToAndReturnsFromRepository() {
        // Given
        Round roundSaving = new Round();
        roundSaving.setName("Some Round");
        Round roundExpected = new Round();
        given(roundRepository.save(roundSaving)).willReturn(roundExpected);

        // When
        Round roundActual = roundService.createRound(123L, roundSaving);

        // Then
        verify(roundRepository).save(roundSaving);
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testCreateRoundNullsIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Round roundSaving = new Round();
        roundSaving.setId(123L);
        roundSaving.setName("Some Round");
        Round roundExpected = new Round();
        given(roundRepository.save(roundSaving)).willReturn(roundExpected);

        // When
        Round roundActual = roundService.createRound(123L, roundSaving);

        // Then
        assertNull(roundSaving.getId());
        verify(roundRepository).save(roundSaving);
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testCreateRoundSetsQuizIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Round roundSaving = new Round();
        roundSaving.setName("Some Round");
        Long quizIdSaving = 321L;
        Round roundExpected = new Round();
        given(roundRepository.save(roundSaving)).willReturn(roundExpected);

        // When
        Round roundActual = roundService.createRound(quizIdSaving, roundSaving);

        // Then
        assertEquals(quizIdSaving, roundSaving.getQuizId());
        verify(roundRepository).save(roundSaving);
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testGetRoundReturnsExistingRoundFromRepository() {
        // Given
        Round roundExpected = new Round();
        Long idExpected = 321L;
        roundExpected.setName("Some Round");
        given(roundRepository.findById(idExpected)).willReturn(Optional.of(roundExpected));

        // When
        Round roundActual = roundService.getRound(idExpected);

        // Then
        verify(roundRepository).findById(idExpected);
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testGetRoundThrowsExceptionForNonExistingRound() {
        // Given
        Long idExpected = 321L;
        given(roundRepository.findById(idExpected)).willReturn(Optional.empty());

        // When / Then
        assertThrows(ObjectNotFoundException.class, () -> roundService.getRound(idExpected));
    }

    @Test
    public void testGetRoundsReturnsExistingQuizRoundsFromRepository() {
        // Given
        Long quizId = 321L;
        Round roundExpected1 = new Round();
        Round roundExpected2 = new Round();
        roundExpected1.setName("Round 1");
        roundExpected2.setName("Round 2");
        List<Round> roundsExpected = Arrays.asList(roundExpected1, roundExpected2);
        given(roundRepository.findAllByQuizId(quizId)).willReturn(roundsExpected);

        // When
        List<Round> roundsActual = roundService.getRounds(quizId);

        // Then
        verify(roundRepository).findAllByQuizId(quizId);
        assertEquals(roundsExpected, roundsActual);
    }

    @Test
    public void testUpdateRoundSavesToAndReturnsFromRepository() {
        // Given
        Round roundSaving = new Round();
        roundSaving.setName("Some Round");
        Round roundExpected = new Round();
        given(roundRepository.save(roundSaving)).willReturn(roundExpected);

        // When
        Round roundActual = roundService.updateRound(321L, roundSaving);

        // Then
        verify(roundRepository).save(roundSaving);
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testUpdateRoundSetsIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Round roundSaving = new Round();
        roundSaving.setId(123L);
        roundSaving.setName("Some Round");
        Round roundExpected = new Round();
        Long idExpected = 321L;
        roundExpected.setId(idExpected);
        given(roundRepository.save(roundSaving)).willReturn(roundExpected);

        // When
        Round roundActual = roundService.updateRound(idExpected, roundSaving);

        // Then
        assertEquals(idExpected, roundSaving.getId());
        verify(roundRepository).save(roundSaving);
        assertEquals(roundExpected, roundActual);
    }

    @Test
    public void testDeleteRoundCallsDeleteOnRepository() {
        // Given
        Long idDeleting = 321L;

        // When
        roundService.deleteRound(idDeleting);

        // Then
        verify(roundRepository).deleteById(idDeleting);
    }

}
