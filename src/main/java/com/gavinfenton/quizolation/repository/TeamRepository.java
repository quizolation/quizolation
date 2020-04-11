package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
