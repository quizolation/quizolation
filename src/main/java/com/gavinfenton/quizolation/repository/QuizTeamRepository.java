package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.QuizTeam;
import com.gavinfenton.quizolation.entity.QuizTeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTeamRepository extends JpaRepository<QuizTeam, QuizTeamId> {
}
