package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.QuestionDTO;
import com.gavinfenton.quizolation.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDTO toDTO(Question question);

    List<QuestionDTO> toDTOList(List<Question> questionList);

}
