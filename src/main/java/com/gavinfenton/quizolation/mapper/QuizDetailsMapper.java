package com.gavinfenton.quizolation.mapper;

import com.gavinfenton.quizolation.dto.QuizDetailsDTO;
import com.gavinfenton.quizolation.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizDetailsMapper {

    QuizDetailsMapper INSTANCE = Mappers.getMapper(QuizDetailsMapper.class);

    QuizDetailsDTO toDTO(Quiz quiz);

    List<QuizDetailsDTO> toDTOList(List<Quiz> quiz);

    Quiz toQuiz(QuizDetailsDTO dto);

}
