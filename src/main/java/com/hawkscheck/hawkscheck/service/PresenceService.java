package com.hawkscheck.hawkscheck.service;


import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.model.Presence;
import com.hawkscheck.hawkscheck.model.StatusPresenceEnum;
import com.hawkscheck.hawkscheck.repository.PresenceRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PresenceService {
    
    @Autowired
    private PresenceRepository presenceRepository;

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

    


    
}
