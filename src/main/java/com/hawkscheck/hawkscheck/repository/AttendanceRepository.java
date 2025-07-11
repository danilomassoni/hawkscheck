package com.hawkscheck.hawkscheck.repository;

import com.hawkscheck.hawkscheck.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByTeamId(Long teamId);
    List<Attendance> findByStudentId(Long studentId);
}
