package com.hawkscheck.hawkscheck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hawkscheck.hawkscheck.model.Student;
import com.hawkscheck.hawkscheck.model.Team;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByTeam(Team team);
    List<Student> findByTeamId(Long teamId);
    // Custom query methods can be defined here if needed
    // For example, you can add methods to find students by specific criteria
    
}
