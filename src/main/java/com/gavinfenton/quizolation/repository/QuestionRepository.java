package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
