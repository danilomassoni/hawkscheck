package com.hawkscheck.hawkscheck.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hawkscheck.hawkscheck.dto.TeamRequestDTO;
import com.hawkscheck.hawkscheck.dto.TeamResponseDTO;
import com.hawkscheck.hawkscheck.dto.UserResponseDTO;
import com.hawkscheck.hawkscheck.service.TeamService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {
    
    private final TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO dto, Authentication auth) {
        return ResponseEntity.ok(teamService.createTeam(dto, auth.getName()));
    }
    
    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/{teamId}/add-student/{studentId}")
    public ResponseEntity<?> addStudent(@PathVariable Long teamId, @PathVariable Long studentId) {
        teamService.addStudentToTeam(teamId, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<List<TeamResponseDTO>> getMyTeams(Authentication auth) {
    return ResponseEntity.ok(teamService.getTeamsByMentor(auth.getName()));
    }

    @GetMapping("/{teamId}/members")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<List<UserResponseDTO>> getTeamMembers(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getTeamMembers(teamId));
    }


}
