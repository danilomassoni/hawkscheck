package com.hawkscheck.hawkscheck.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.dto.StudentRequestDTO;
import com.hawkscheck.hawkscheck.dto.StudentResponseDTO;
import com.hawkscheck.hawkscheck.model.Student;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.repository.StudentRepository;
import com.hawkscheck.hawkscheck.repository.TeamRepository;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        Optional<Team> teamOptional = teamRepository.findById(dto.getTeamId());

        if (teamOptional.isEmpty()) {
            throw new RuntimeException("Team not found with id: " + dto.getTeamId());
        }

        Student student = new Student();
        student.setName(dto.getName());
        student.setRm(dto.getRm());
        student.setTeam(teamOptional.get());

        Student saved = studentRepository.save(student);
        return new StudentResponseDTO(
            saved.getId(),
            saved.getName(),
            saved.getRm(),
            saved.getTeam().getName(),
            saved.getTeam().getId()
        );
    }

    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll()
            .stream()
            .map(student -> new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getRm(),
                student.getTeam().getName(),
                student.getTeam().getId()
            ))
            .collect(Collectors.toList());
    }

    public Optional<StudentResponseDTO> findById(Long id) {
        return studentRepository.findById(id)
            .map(student -> new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getRm(),
                student.getTeam().getName(),
                student.getTeam().getId()
                ));
    }

    public List<StudentResponseDTO> findByTeam(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);

        if (team.isEmpty()) {
            return List.of(); // or throw an exception, depending on the rule
        }

        return studentRepository.findByTeam(team.get())
            .stream()
            .map(student -> new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getRm(),
                student.getTeam().getName(),
                student.getTeam().getId()
                ))
                .collect(Collectors.toList());

    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        Student studentExists = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        studentExists.setName(studentRequestDTO.getName());
        studentExists.setRm(studentRequestDTO.getRm());

        if (studentRequestDTO.getTeamId() != null) {
            Team team = teamRepository.findById(studentRequestDTO.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));
            studentExists.setTeam(team);
        }

        Student studentUpdated = studentRepository.save(studentExists);

        StudentResponseDTO response = new StudentResponseDTO();
        response.setId(studentUpdated.getId());
        response.setName(studentUpdated.getName());
        response.setRm(studentUpdated.getRm());
        response.setTeamId(studentUpdated.getTeam().getId());
    
        return response;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    


}
