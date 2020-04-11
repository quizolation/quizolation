package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {
}
