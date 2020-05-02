package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByRoundId(Long roundId);

    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Question qn " +
            "LEFT JOIN Round r ON r.id = qn.roundId " +
            "LEFT JOIN Quiz q ON q.id = r.quizId " +
            "WHERE qn.id = :questionId AND q.masterId = :userId")
    boolean isMasterOfRelatedQuiz(@Param("questionId") Long questionId, @Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Question qn " +
            "LEFT JOIN Round r ON r.id = qn.roundId " +
            "LEFT JOIN QuizTeam qt ON qt.quizId = r.quizId " +
            "LEFT JOIN TeamUser tu ON tu.teamId = qt.teamId " +
            "WHERE qn = :questionId AND tu.userId = :userId")
    boolean isTeamMemberOfRelatedQuiz(@Param("questionId") Long questionId, @Param("userId") Long userId);

}
