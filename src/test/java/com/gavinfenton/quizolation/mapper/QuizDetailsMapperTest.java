package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.QuizDetailsDTO;
import com.gavinfenton.quizolation.entity.Quiz;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QuizDetailsMapperTest {

    QuizDetailsMapper quizDetailsMapper = QuizDetailsMapper.INSTANCE;

    @Test
    public void testToDTOMapsToAllDTOFields() {
        // Given
        Quiz quiz = new Quiz();
        quiz.setId(123L);
        quiz.setName("Some quiz");

        // When
        QuizDetailsDTO dto = quizDetailsMapper.toDTO(quiz);

        // Then
        assertEquals(quiz.getId(), dto.getId());
        assertEquals(quiz.getName(), dto.getName());
    }

    @Test
    public void testToQuestionMapsFromAllDTOFields() {
        // Given
        QuizDetailsDTO dto = new QuizDetailsDTO();
        dto.setId(123L);
        dto.setName("Some quiz");

        // When
        Quiz quiz = quizDetailsMapper.toQuiz(dto);

        // Then
        assertEquals(dto.getId(), quiz.getId());
        assertNull(quiz.getMasterId());
        assertEquals(dto.getName(), quiz.getName());
    }

    @Test
    public void testToDTOListMapsToAllDTOFields() {
        /// Given
        Quiz quiz = new Quiz();
        quiz.setId(123L);
        quiz.setName("Some quiz");
        List<Quiz> quizList = Collections.singletonList(quiz);

        // When
        List<QuizDetailsDTO> dtoList = quizDetailsMapper.toDTOList(quizList);
        QuizDetailsDTO dto = dtoList.get(0);

        // Then
        assertEquals(quiz.getId(), dto.getId());
        assertEquals(quiz.getName(), dto.getName());
    }

}
