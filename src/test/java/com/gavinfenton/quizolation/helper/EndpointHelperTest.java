package com.gavinfenton.quizolation.helper;

import com.gavinfenton.quizolation.constant.Endpoints;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndpointHelperTest {

    @Test
    public void testInsertIdsInsertsSingleId() {
        // Given
        Long quizId = 123L;
        String expected = String.format("/quizzes/%s", quizId);

        // When
        String actual = EndpointHelper.insertIds(Endpoints.QUIZ, quizId);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testInsertIdsInsertsMultipleIds() {
        // Given
        Long quizId = 123L;
        Long roundId = 345L;
        String expected = String.format("/quizzes/%s/rounds/%s", quizId, roundId);

        // When
        String actual = EndpointHelper.insertIds(Endpoints.ROUND, quizId, roundId);

        // Then
        assertEquals(expected, actual);
    }

}
