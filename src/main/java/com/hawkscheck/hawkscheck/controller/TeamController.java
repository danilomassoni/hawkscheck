package com.hawkscheck.hawkscheck.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hawkscheck.hawkscheck.dto.TeamRequestDTO;
import com.hawkscheck.hawkscheck.dto.TeamResponseDTO;
import com.hawkscheck.hawkscheck.service.TeamService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @PostMapping("/{teamId}/add-student/{studentId}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<?> addStudent(@PathVariable Long teamId, @PathVariable Long studentId) {
        teamService.addStudentToTeam(teamId, studentId);
        return ResponseEntity.ok().build();
    }

}
