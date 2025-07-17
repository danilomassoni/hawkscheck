package com.hawkscheck.hawkscheck.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.dto.TeamRequestDTO;
import com.hawkscheck.hawkscheck.dto.TeamResponseDTO;
import com.hawkscheck.hawkscheck.dto.UserResponseDTO;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.TeamRepository;
import com.hawkscheck.hawkscheck.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
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

    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findAll()
        .stream()
        .map(team -> new TeamResponseDTO(team.getId(), team.getName(), team.getMentor().getName()))
        .toList();
    }

    public List<TeamResponseDTO> getTeamsByMentor(String mentorEmail) {
        User mentor = userRepository.findByEmail(mentorEmail)
            .orElseThrow(() -> new UsernameNotFoundException("Mentor not found"));

        List<Team> teams = teamRepository.findByMentor(mentor);

        return teams.stream()
            .map(team -> new TeamResponseDTO(team.getId(), team.getName(), mentor.getName() ))
            .toList();
    }

    public List<UserResponseDTO> getTeamMembers(Long teamId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new RuntimeException("Team not found"));

    List<User> students = userRepository.findByTeam(team);
    return students.stream()
        .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPaper()))
        .toList();
    }

    public TeamResponseDTO getTeamById(Long teamId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new RuntimeException("Equipe não encontrada com ID: " + teamId));

    return TeamResponseDTO.builder()
        .id(team.getId())
        .name(team.getName())
        .mentorId(team.getMentor().getId())
        .mentorName(team.getMentor().getName())
        .build();
    }


    public void deleteTeam(Long teamId, String mentorEmail) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));

    // Valida se o mentor dono está realizando a exclusão
    if (!team.getMentor().getEmail().equals(mentorEmail)) {
        throw new RuntimeException("Você não tem permissão para excluir esta equipe.");
    }

    // Remove a referência do time dos estudantes antes de excluir o time
    List<User> students = userRepository.findByTeam(team);
    for (User student : students) {
        student.setTeam(null);
        userRepository.save(student);
    }

    teamRepository.delete(team);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }



    



}
