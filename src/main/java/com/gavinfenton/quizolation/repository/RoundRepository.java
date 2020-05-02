package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {

    List<Round> findAllByQuizId(Long quizId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Round r " +
            "LEFT JOIN Quiz q ON q.id = r.quizId " +
            "WHERE r.id = :roundId AND q.masterId = :userId")
    boolean isMasterOfRelatedQuiz(@Param("roundId") Long roundId, @Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Round r " +
            "LEFT JOIN QuizTeam qt ON qt.quizId = r.quizId " +
            "LEFT JOIN TeamUser tu ON tu.teamId = qt.teamId " +
            "WHERE r = :roundId AND tu.userId = :userId")
    boolean isTeamMemberOfRelatedQuiz(@Param("roundId") Long roundId, @Param("userId") Long userId);

}
