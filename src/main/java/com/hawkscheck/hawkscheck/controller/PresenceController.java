package com.hawkscheck.hawkscheck.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hawkscheck.hawkscheck.dto.FrequencyReportDTO;
import com.hawkscheck.hawkscheck.dto.StudentFrequencyReportDTO;
import com.hawkscheck.hawkscheck.model.Presence;
import com.hawkscheck.hawkscheck.model.StatusPresenceEnum;
import com.hawkscheck.hawkscheck.service.PresenceService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    @PostMapping 
    public ResponseEntity<Presence> registerPresence(@RequestBody Presence presence) {
        Presence saved = presenceService.savePresence(presence);
        return ResponseEntity.ok(saved);
    }

    @GetMapping  
    public ResponseEntity<List<Presence>> listAll() {
        return ResponseEntity.ok(presenceService.findAllPresences());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Presence>> byStudent(@PathVariable Long id) {
        return ResponseEntity.ok(presenceService.findByStudent(id));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Presence>> byDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(presenceService.findByDate(date));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Presence>> byStatus(@PathVariable StatusPresenceEnum status) {
        return ResponseEntity.ok(presenceService.findByStatus(status));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Presence>> filterByPeriod(
        @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
            return ResponseEntity.ok(presenceService.findByDateBetween(start, end));
        } 

    @GetMapping("/export")
    public void exportCsv(
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, 
        @RequestParam("teamId") Long teamId,
        HttpServletResponse  response) throws IOException {
            presenceService.exportToCsv(date, teamId, response);
        }

    @GetMapping("/frequency/team/{teamId}")
    public ResponseEntity<List<FrequencyReportDTO>> getFrequencyByTeam(@PathVariable Long teamId, 
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) 
        {
        return ResponseEntity.ok(presenceService.getFrequencyByTeam(teamId, startDate, endDate));
    }

    @GetMapping("/frequency/team/{teamId}/csv")
    public void exportFrequencyCsv(
        @PathVariable Long teamId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        HttpServletResponse response)
        throws IOException {
            List<FrequencyReportDTO> report = presenceService.getFrequencyByTeam(teamId, startDate, endDate);

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; fileman=\"frequency_report.csv\"");

            try (PrintWriter writer = response.getWriter()) {
                writer.println("Student ID, Student Name, Team Name, Total, Present, Absent, Percentage ");

                for (FrequencyReportDTO dto : report) {
                    writer.printf("%d,%s,%s,%d,%d,%d,%.2f%%\n",
                        dto.getStudentId(),
                        dto.getStudentName(),
                        dto.getTeamName(),
                        dto.getTotal(),
                        dto.getPresent(),
                        dto.getAbsent(),
                        dto.getPercentage());
                        
                }
            }
        }
        
    @GetMapping("/frequency/student/{studentId}")
    public ResponseEntity<StudentFrequencyReportDTO> getFrequencyByStudent(@PathVariable Long studentId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
            return ResponseEntity.ok(presenceService.getFrequencyByStudent(studentId, startDate, endDate));
        }

    @GetMapping("/frequency/student/{studentId}/csv")
    public void exportStudentFrquencyCsv(@PathVariable Long studentId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        HttpServletResponse response) throws IOException {

            StudentFrequencyReportDTO dto = presenceService.getFrequencyByStudent(studentId, startDate, endDate);

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; fileman=\"student_frequency_report.csv\"");

            try (PrintWriter writer = response.getWriter()) {
                writer.println("Student ID, Student Name, Team Name, Total, Present, Absent, Percentage ");
                
                writer.printf("%d,%s,%s,%d,%d,%d,%.2f%%\n",
                    dto.getStudentId(),
                    dto.getStudentName(),
                    dto.getTeamName(),
                    dto.getTotal(),
                    dto.getPresent(),
                    dto.getAbsent(),
                    dto.getPercentage());
            }
        }
    
    
    
    
}
