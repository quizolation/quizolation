package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.QuestionAndAnswerDTO;
import com.gavinfenton.quizolation.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionAndAnswerMapper {

    QuestionAndAnswerMapper INSTANCE = Mappers.getMapper(QuestionAndAnswerMapper.class);

    QuestionAndAnswerDTO toDTO(Question question);

    Question toQuestion(QuestionAndAnswerDTO dto);

    List<QuestionAndAnswerDTO> toDTOList(List<Question> question);

}
