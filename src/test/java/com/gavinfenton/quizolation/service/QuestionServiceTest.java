package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.repository.QuestionRepository;
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

public class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private RoundService roundService;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateQuestionSavesToAndReturnsFromRepository() {
        // Given
        Question questionSaving = new Question();
        questionSaving.setQuestion("Some Question");
        Question questionExpected = new Question();
        given(questionRepository.save(questionSaving)).willReturn(questionExpected);

        // When
        Question questionActual = questionService.createQuestion(123L, questionSaving);

        // Then
        verify(questionRepository).save(questionSaving);
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testCreateQuestionNullsIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Question questionSaving = new Question();
        questionSaving.setId(123L);
        questionSaving.setQuestion("Some Question");
        Question questionExpected = new Question();
        given(questionRepository.save(questionSaving)).willReturn(questionExpected);

        // When
        Question questionActual = questionService.createQuestion(123L, questionSaving);

        // Then
        assertNull(questionSaving.getId());
        verify(questionRepository).save(questionSaving);
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testCreateQuestionSetsRoundIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Question questionSaving = new Question();
        questionSaving.setQuestion("Some Question");
        Long roundIdSaving = 321L;
        Question questionExpected = new Question();
        given(questionRepository.save(questionSaving)).willReturn(questionExpected);

        // When
        Question questionActual = questionService.createQuestion(roundIdSaving, questionSaving);

        // Then
        assertEquals(roundIdSaving, questionSaving.getRoundId());
        verify(questionRepository).save(questionSaving);
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testGetQuestionReturnsExistingQuestionFromRepository() {
        // Given
        Question questionExpected = new Question();
        Long idExpected = 321L;
        questionExpected.setQuestion("Some Question");
        given(questionRepository.findById(idExpected)).willReturn(Optional.of(questionExpected));

        // When
        Question questionActual = questionService.getQuestion(idExpected);

        // Then
        verify(questionRepository).findById(idExpected);
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testGetQuestionThrowsExceptionForNonExistingQuestion() {
        // Given
        Long idExpected = 321L;
        given(questionRepository.findById(idExpected)).willReturn(Optional.empty());

        // When / Then
        assertThrows(ObjectNotFoundException.class, () -> questionService.getQuestion(idExpected));
    }

    @Test
    public void testGetQuestionsReturnsExistingRoundQuestionsFromRepository() {
        // Given
        Long roundId = 321L;
        Question questionExpected1 = new Question();
        Question questionExpected2 = new Question();
        questionExpected1.setQuestion("Question 1");
        questionExpected2.setQuestion("Question 2");
        List<Question> questionsExpected = Arrays.asList(questionExpected1, questionExpected2);
        given(questionRepository.findAllByRoundId(roundId)).willReturn(questionsExpected);

        // When
        List<Question> questionsActual = questionService.getQuestions(roundId);

        // Then
        verify(questionRepository).findAllByRoundId(roundId);
        assertEquals(questionsExpected, questionsActual);
    }

    @Test
    public void testUpdateQuestionSavesToAndReturnsFromRepository() {
        // Given
        Question questionSaving = new Question();
        questionSaving.setQuestion("Some Question");
        Question questionExpected = new Question();
        given(questionRepository.save(questionSaving)).willReturn(questionExpected);

        // When
        Question questionActual = questionService.updateQuestion(321L, questionSaving);

        // Then
        verify(questionRepository).save(questionSaving);
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testUpdateQuestionSetsIdBeforeSaveAndReturnsFromRepository() {
        // Given
        Question questionSaving = new Question();
        questionSaving.setId(123L);
        questionSaving.setQuestion("Some Question");
        Question questionExpected = new Question();
        Long idExpected = 321L;
        questionExpected.setId(idExpected);
        given(questionRepository.save(questionSaving)).willReturn(questionExpected);

        // When
        Question questionActual = questionService.updateQuestion(idExpected, questionSaving);

        // Then
        assertEquals(idExpected, questionSaving.getId());
        verify(questionRepository).save(questionSaving);
        assertEquals(questionExpected, questionActual);
    }

    @Test
    public void testDeleteQuestionCallsDeleteOnRepository() {
        // Given
        Long idDeleting = 321L;

        // When
        questionService.deleteQuestion(idDeleting);

        // Then
        verify(questionRepository).deleteById(idDeleting);
    }

}
