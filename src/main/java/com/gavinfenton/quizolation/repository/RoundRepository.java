package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {

    List<Round> findAllByQuizId(Long quizId);

}
