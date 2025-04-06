package com.hawkscheck.hawkscheck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hawkscheck.hawkscheck.dto.TeamRequestDTO;
import com.hawkscheck.hawkscheck.dto.TeamResponseDTO;
import com.hawkscheck.hawkscheck.service.TeamService;

@RestController 
@RequestMapping("/api/teams")
public class TeamController {
    
    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<TeamResponseDTO> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public TeamResponseDTO getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @PostMapping
    public TeamResponseDTO createTeam(@RequestBody TeamRequestDTO dto) {
        return teamService.createTeam(dto);
    }

    @PutMapping("/{id}")
    public TeamResponseDTO update(@PathVariable Long id, @RequestBody TeamRequestDTO dto) {
        return teamService.updateTeam(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
    }
    
}
