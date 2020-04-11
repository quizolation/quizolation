package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
