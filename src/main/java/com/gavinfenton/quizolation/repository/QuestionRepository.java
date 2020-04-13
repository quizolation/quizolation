package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByRoundId(Long roundId);

}
