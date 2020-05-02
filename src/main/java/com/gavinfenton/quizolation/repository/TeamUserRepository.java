package com.gavinfenton.quizolation.repository;

import com.gavinfenton.quizolation.entity.TeamUser;
import com.gavinfenton.quizolation.entity.TeamUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUser, TeamUserId> {

}
