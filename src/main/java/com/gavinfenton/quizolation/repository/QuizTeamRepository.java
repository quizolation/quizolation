package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.QuizTeam;
import com.gavinfenton.quizolation.entity.QuizTeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTeamRepository extends JpaRepository<QuizTeam, QuizTeamId> {

    @Query("SELECT CASE WHEN COUNT(qt) > 0 THEN TRUE ELSE FALSE END " +
            "FROM QuizTeam qt " +
            "LEFT JOIN TeamUser tu ON tu.teamId = qt.teamId " +
            "WHERE qt.quizId = :quizId AND tu.userId = :userId")
    boolean isQuizTeamMember(@Param("quizId") Long quizId, @Param("userId") Long userId);

}
