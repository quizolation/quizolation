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
        String actual = EndpointHelper.insertId(Endpoints.QUIZ, quizId);

        // Then
        assertEquals(expected, actual);
    }

}
