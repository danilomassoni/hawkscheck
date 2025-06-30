package com.hawkscheck.hawkscheck.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.dto.TeamRequestDTO;
import com.hawkscheck.hawkscheck.dto.TeamResponseDTO;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.TeamRepository;
import com.hawkscheck.hawkscheck.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {
    
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamResponseDTO createTeam(TeamRequestDTO dto, String mentorEmail) {
        User mentor = userRepository.findByEmail(mentorEmail)
            .orElseThrow(() -> new UsernameNotFoundException("Mentor not found"));

        Team team = new Team();
        team.setName(dto.getName());
        team.setMentor(mentor);
        teamRepository.save(team);

        return new TeamResponseDTO(team.getId(), team.getName(), mentor.getName());
        
    }

    public void addStudentToTeam(Long teamId, Long studentId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new RuntimeException("Team not found"));

    User student = userRepository.findById(studentId)
        .orElseThrow(() -> new UsernameNotFoundException("Student not found"));

    // Verifica se é mesmo um STUDENT
    if (!student.getPaper().name().equals("STUDENT")) {
        throw new RuntimeException("Only STUDENT users can be added to a team.");
    }

    student.setTeam(team);
    userRepository.save(student);
}


}
