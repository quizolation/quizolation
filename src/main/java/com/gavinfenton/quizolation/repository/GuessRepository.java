package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Guess;
import com.gavinfenton.quizolation.entity.GuessId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuessRepository extends JpaRepository<Guess, GuessId> {
}
