package com.hawkscheck.hawkscheck.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.dto.TeamRequestDTO;
import com.hawkscheck.hawkscheck.dto.TeamResponseDTO;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.repository.TeamRepository;

@Service
public class TeamService {
    
    @Autowired
    private TeamRepository teamRepository;

    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findAll().stream()
            .map(this::toDTO).toList();
    }

    public TeamResponseDTO getTeamById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.map(this::toDTO).orElse(null);
    }

    public TeamResponseDTO createTeam(TeamRequestDTO dto) {
        Team team = new Team();
        team.setName(dto.getName());
        return toDTO(teamRepository.save(team));
    }

    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO dto) {
        Optional<Team> optional = teamRepository.findById(id);
        if (optional.isPresent()) {
            Team team = optional.get();
            team.setName(dto.getName());
            return toDTO(teamRepository.save(team));
        }
        return null;
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    private TeamResponseDTO toDTO(Team team) {
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        return dto;
    }
}
