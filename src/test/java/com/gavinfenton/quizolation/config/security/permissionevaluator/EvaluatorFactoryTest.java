package com.gavinfenton.quizolation.config.security.permissionevaluator;

import com.gavinfenton.quizolation.entity.Question;
import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.entity.Round;
import com.gavinfenton.quizolation.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class EvaluatorFactoryTest {

    @InjectMocks
    private EvaluatorFactory evaluatorFactory;

    @Mock
    private QuestionEvaluator questionEvaluator;

    @Mock
    private QuizEvaluator quizEvaluator;

    @Mock
    private RoundEvaluator roundEvaluator;

    @Mock
    private TeamEvaluator teamEvaluator;

    private Map<String, Evaluator<?>> subEvaluators;

    @BeforeEach
    public void setup() {
        initMocks(this);
        subEvaluators = evaluatorFactory.permissionEvaluators();
    }

    @Test
    public void testQuizEvaluatorIsReturned() {
        // Given
        String className = "Quiz";
        Evaluator<Quiz> expected = quizEvaluator;

        // When
        Evaluator<?> actual = subEvaluators.get(className);

        // Then
        assertEquals(expected, actual);
        assertEquals(className, Quiz.class.getSimpleName());
    }

    @Test
    public void testRoundEvaluatorIsReturned() {
        // Given
        String className = "Round";
        Evaluator<Round> expected = roundEvaluator;

        // When
        Evaluator<?> actual = subEvaluators.get(className);

        // Then
        assertEquals(expected, actual);
        assertEquals(className, Round.class.getSimpleName());
    }

    @Test
    public void testQuestionEvaluatorIsReturned() {
        // Given
        String className = "Question";
        Evaluator<Question> expected = questionEvaluator;

        // When
        Evaluator<?> actual = subEvaluators.get(className);

        // Then
        assertEquals(expected, actual);
        assertEquals(className, Question.class.getSimpleName());
    }

    @Test
    public void testTeamEvaluatorIsReturned() {
        // Given
        String className = "Team";
        Evaluator<Team> expected = teamEvaluator;

        // When
        Evaluator<?> actual = subEvaluators.get(className);

        // Then
        assertEquals(expected, actual);
        assertEquals(className, Team.class.getSimpleName());
    }

}
