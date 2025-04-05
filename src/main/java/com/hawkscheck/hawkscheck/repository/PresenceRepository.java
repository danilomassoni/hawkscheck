package com.hawkscheck.hawkscheck.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hawkscheck.hawkscheck.model.Presence;
import com.hawkscheck.hawkscheck.model.Student;

public interface PresenceRepository extends JpaRepository<Presence, Long> {
    List<Presence> findByStudent(Student student);
    List<Presence> findByDate(LocalDate date);
    
}
