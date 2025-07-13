package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.AttendanceRequestDTO;
import com.hawkscheck.hawkscheck.dto.AttendanceResponseDTO;
import com.hawkscheck.hawkscheck.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<List<AttendanceResponseDTO>> create(@RequestBody AttendanceRequestDTO dto) {
        return ResponseEntity.ok(attendanceService.create(dto));
    }


    @GetMapping("/team/{teamId}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<List<AttendanceResponseDTO>> listByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(attendanceService.listByTeam(teamId));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<List<AttendanceResponseDTO>> listByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.listByStudent(studentId));
    }
}
