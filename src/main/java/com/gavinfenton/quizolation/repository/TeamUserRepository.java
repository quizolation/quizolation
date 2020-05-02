package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.TeamUser;
import com.gavinfenton.quizolation.entity.TeamUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUser, TeamUserId> {

    @Query("SELECT CASE WHEN COUNT(tu) > 0 THEN TRUE ELSE FALSE END " +
            "FROM TeamUser tu " +
            "WHERE tu.teamId = :teamId AND tu.userId = :userId")
    boolean isTeamMember(@Param("teamId") Long teamId, @Param("userId") Long userId);

}
