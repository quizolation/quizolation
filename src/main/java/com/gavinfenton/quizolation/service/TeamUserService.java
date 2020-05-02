package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.TeamUser;
import com.gavinfenton.quizolation.repository.TeamUserRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamUserService {

    private final TeamUserRepository teamUserRepository;

    public TeamUserService(TeamUserRepository teamUserRepository) {
        this.teamUserRepository = teamUserRepository;
    }

    public void addUserToTeam(Long teamId, Long userId) {
        TeamUser teamUser = new TeamUser();
        teamUser.setTeamId(teamId);
        teamUser.setUserId(userId);

        teamUserRepository.save(teamUser);
    }

    public boolean isQuizTeamMember(Long teamId, Long userId) {
        return teamUserRepository.isTeamMember(teamId, userId);
    }

}
