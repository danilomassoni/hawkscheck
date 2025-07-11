package com.hawkscheck.hawkscheck.service;

import com.hawkscheck.hawkscheck.dto.AttendanceRequestDTO;
import com.hawkscheck.hawkscheck.dto.AttendanceResponseDTO;
import com.hawkscheck.hawkscheck.model.Attendance;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.AttendanceRepository;
import com.hawkscheck.hawkscheck.repository.TeamRepository;
import com.hawkscheck.hawkscheck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public AttendanceResponseDTO create(AttendanceRequestDTO dto) {
        User student = userRepository.findById(dto.getStudentId()).orElseThrow();
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow();

        Attendance attendance = Attendance.builder()
                .student(student)
                .team(team)
                .date(dto.getDate())
                .present(dto.isPresent())
                .build();

        Attendance saved = attendanceRepository.save(attendance);

        return toDTO(saved);
    }

    public List<AttendanceResponseDTO> listByTeam(Long teamId) {
        return attendanceRepository.findByTeamId(teamId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDTO> listByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AttendanceResponseDTO toDTO(Attendance attendance) {
        return AttendanceResponseDTO.builder()
                .id(attendance.getId())
                .date(attendance.getDate())
                .present(attendance.isPresent())
                .studentId(attendance.getStudent().getId())
                .studentName(attendance.getStudent().getName())
                .teamId(attendance.getTeam().getId())
                .teamName(attendance.getTeam().getName())
                .build();
    }
}
