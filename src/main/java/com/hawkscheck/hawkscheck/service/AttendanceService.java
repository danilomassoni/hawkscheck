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

    public List<AttendanceResponseDTO> create(AttendanceRequestDTO dto) {
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Equipe com ID " + dto.getTeamId() + " não encontrada"));

        List<Attendance> attendances = dto.getRecords().stream()
                .map(record -> {
                    if (record.getStudentId() == null) {
                        throw new IllegalArgumentException("ID do aluno não pode ser null");
                    }

                    User student = userRepository.findById(record.getStudentId())
                            .orElseThrow(() -> new RuntimeException("Aluno com ID " + record.getStudentId() + " não encontrado"));

                    // VERIFICA se já existe presença para esse aluno, time e data
                    boolean alreadyExists = attendanceRepository.existsByStudentIdAndTeamIdAndDate(
                            student.getId(), team.getId(), dto.getDate()
                    );
                    if (alreadyExists) {
                        throw new IllegalStateException(
                                "Já existe uma presença registrada para o aluno " + student.getName() + " em " + dto.getDate()
                        );
                    }

                    return Attendance.builder()
                            .student(student)
                            .team(team)
                            .date(dto.getDate())
                            .present(record.isPresent())
                            .build();
                })
                .collect(Collectors.toList());

        List<Attendance> saved = attendanceRepository.saveAll(attendances);

        return saved.stream().map(this::toDTO).collect(Collectors.toList());
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
