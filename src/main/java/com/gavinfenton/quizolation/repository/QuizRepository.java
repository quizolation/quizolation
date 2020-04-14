package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Quiz;
import com.gavinfenton.quizolation.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
