package com.gavinfenton.quizolation.service;

import com.gavinfenton.quizolation.entity.Team;
import com.gavinfenton.quizolation.helper.SecurityHelper;
import com.gavinfenton.quizolation.repository.TeamRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team createTeam(Team team) {
        team.setId(null);
        team.setLeaderId(SecurityHelper.getUserId());

        return teamRepository.save(team);
    }

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Team"));
    }

    public Team updateTeam(Long id, Team team) {
        Team existing = getTeam(id);
        existing.setName(team.getName());

        return teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

}
