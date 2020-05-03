package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.QuestionDTO;
import com.gavinfenton.quizolation.entity.Question;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionMapperTest {

    QuestionMapper questionMapper = QuestionMapper.INSTANCE;

    @Test
    public void testToDTOMapsToAllDTOFields() {
        // Given
        Question question = new Question();
        question.setId(123L);
        question.setQuestion("What is?");
        question.setAnswer("IDK");
        question.setPoints(321);

        // When
        QuestionDTO dto = questionMapper.toDTO(question);

        // Then
        assertEquals(question.getId(), dto.getId());
        assertEquals(question.getQuestion(), dto.getQuestion());
        assertEquals(question.getPoints(), dto.getPoints());
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
        List<QuestionDTO> dtoList = questionMapper.toDTOList(questionList);
        QuestionDTO dto = dtoList.get(0);

        // Then
        assertEquals(1, dtoList.size());
        assertEquals(question.getId(), dto.getId());
        assertEquals(question.getQuestion(), dto.getQuestion());
        assertEquals(question.getPoints(), dto.getPoints());
    }

}
