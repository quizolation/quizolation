package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.QuestionAndAnswerDTO;
import com.gavinfenton.quizolation.entity.Question;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QuestionAndAnswerMapperTest {

    QuestionAndAnswerMapper questionAndAnswerMapper = QuestionAndAnswerMapper.INSTANCE;

    @Test
    public void testToDTOMapsToAllDTOFields() {
        // Given
        Question question = new Question();
        question.setId(123L);
        question.setQuestion("What is?");
        question.setAnswer("IDK");
        question.setPoints(321);

        // When
        QuestionAndAnswerDTO dto = questionAndAnswerMapper.toDTO(question);

        // Then
        assertEquals(question.getId(), dto.getId());
        assertEquals(question.getQuestion(), dto.getQuestion());
        assertEquals(question.getAnswer(), dto.getAnswer());
        assertEquals(question.getPoints(), dto.getPoints());
    }

    @Test
    public void testToQuestionMapsFromAllDTOFields() {
        // Given
        QuestionAndAnswerDTO dto = new QuestionAndAnswerDTO();
        dto.setId(123L);
        dto.setQuestion("What is?");
        dto.setAnswer("IDK");
        dto.setPoints(321);

        // When
        Question question = questionAndAnswerMapper.toQuestion(dto);

        // Then
        assertEquals(dto.getId(), question.getId());
        assertNull(question.getRoundId());
        assertEquals(dto.getQuestion(), question.getQuestion());
        assertEquals(dto.getAnswer(), question.getAnswer());
        assertEquals(dto.getPoints(), question.getPoints());
    }

    @Test
    public void testToDTOListMapsToAllDTOFields() {
        // Given
        Question question = new Question();
        question.setId(123L);
        question.setQuestion("What is?");
        question.setAnswer("IDK");
        question.setPoints(321);
        List<Question> questionList = Collections.singletonList(question);

        // When
        List<QuestionAndAnswerDTO> dtoList = questionAndAnswerMapper.toDTOList(questionList);
        QuestionAndAnswerDTO dto = dtoList.get(0);

        // Then
        assertEquals(1, dtoList.size());
        assertEquals(question.getId(), dto.getId());
        assertEquals(question.getQuestion(), dto.getQuestion());
        assertEquals(question.getAnswer(), dto.getAnswer());
        assertEquals(question.getPoints(), dto.getPoints());
    }

}
