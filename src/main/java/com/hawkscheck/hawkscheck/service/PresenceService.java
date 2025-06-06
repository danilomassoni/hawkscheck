package com.hawkscheck.hawkscheck.service;


import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.dto.FrequencyReportDTO;
import com.hawkscheck.hawkscheck.dto.StudentFrequencyReportDTO;
import com.hawkscheck.hawkscheck.model.Presence;
import com.hawkscheck.hawkscheck.model.StatusPresenceEnum;
import com.hawkscheck.hawkscheck.model.Student;
import com.hawkscheck.hawkscheck.repository.PresenceRepository;
import com.hawkscheck.hawkscheck.repository.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PresenceService {
    
    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Presence savePresence(Presence presence) {
        boolean exists = presenceRepository.existsByStudentIdAndDate(
            presence.getStudent().getId(), presence.getDate());
        
        if (exists) {
            throw new IllegalArgumentException("Presence already registered");
        }

        return presenceRepository.save(presence);
    }   

    public List<Presence> findAllPresences() {
        return presenceRepository.findAll();
    }

    public List<Presence> findByStudent(Long studentId) {
        return presenceRepository.findByStudentId(studentId);
    }

    public List<Presence> findByDate(LocalDate date) {
        return presenceRepository.findByDate(date);
    }

    public List<Presence> findByStatus(StatusPresenceEnum status) {
        return presenceRepository.findByStatusPresence(status);
    }

    public List<Presence> findByDateBetween(LocalDate start, LocalDate end) {
        return presenceRepository.findByDateBetween(start, end);
    }

    public void exportToCsv(LocalDate date, Long teamId, HttpServletResponse response) throws  IOException {
        List<Presence> presences = presenceRepository.findByDateAndStudent_TeamId(date, teamId);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=presences.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID, Date, Student, Team, Status, Register by");

        for (Presence p : presences) {
            writer.printf("%d,%s,%s,%s,%s,%s\n",
                p.getId(),
                p.getDate(),
                p.getStudent().getName(),
                p.getStudent().getTeam().getName(),
                p.getStatusPresence(),
                p.getUser().getName()
            );
            writer.flush();
        }
    }

    public List<FrequencyReportDTO> getFrequencyByTeam(Long teamId, LocalDate startDate, LocalDate endDate) {
        List<Student> students = studentRepository.findByTeamId(teamId);
        List<Presence> presences = presenceRepository.findByStudent_TeamId(teamId);

        if (startDate != null && endDate != null) {
            presences = presences.stream()
                .filter(p -> !p.getDate().isBefore(startDate) && !p.getDate().isAfter(endDate))
                .collect(Collectors.toList());
        }

        Map<Long, List<Presence>> presenceMap = presences.stream()
            .collect(Collectors.groupingBy(p -> p.getStudent().getId()));

        List<FrequencyReportDTO> result = new ArrayList<>();

        for (Student student : students) {
            List<Presence> list = presenceMap.getOrDefault(student.getId(), new ArrayList<>());
            long total = list.size();
            long present = list.stream().filter(p -> p.getStatusPresence() == StatusPresenceEnum.PRESENCE).count();
            long absent = list.stream().filter(p -> p.getStatusPresence() == StatusPresenceEnum.LACK).count();
            double percentage = total > 0 ? (present * 100.0) / total :0;

            FrequencyReportDTO dto = new FrequencyReportDTO();
            dto.setStudentId(student.getId());
            dto.setStudentName(student.getName());
            dto.setTeamName(student.getTeam().getName());
            dto.setTotal(total);
            dto.setPresent(present);
            dto.setAbsent(absent);
            dto.setPercentage(percentage);

            result.add(dto);

        }
        return result;

    }

    public StudentFrequencyReportDTO getFrequencyByStudent(Long studentId, LocalDate startDate, LocalDate endDate) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found");
        }

        Student student = studentOpt.get();

        List<Presence> presences = presenceRepository.findByStudentId(studentId);

        if (startDate != null && endDate != null) {
            presences = presences.stream()
                .filter(p -> !p.getDate().isBefore(startDate) && !p.getDate().isAfter(endDate))
                .collect(Collectors.toList());
        }

        long total = presences.size();
        long present = presences.stream().filter(p -> p.getStatusPresence() == StatusPresenceEnum.PRESENCE).count();
        long absent = presences.stream().filter(p -> p.getStatusPresence() == StatusPresenceEnum.LACK).count();
        double percentage = total > 0 ? (present * 100.0) / total : 0;

        StudentFrequencyReportDTO dto = new StudentFrequencyReportDTO();
        dto.setStudentId(student.getId());
        dto.setStudentName(student.getName());
        dto.setTeamName(student.getTeam().getName());
        dto.setTotal(total);
        dto.setPresent(present);
        dto.setAbsent(absent);
        dto.setPercentage(percentage);

        return dto;

    } 

    
}
