package com.hawkscheck.hawkscheck.repository;

import com.hawkscheck.hawkscheck.model.Task;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStudentsContaining(User student);
    List<Task> findByTeam(Team team);

    @Query("SELECT t FROM Task t JOIN t.students s WHERE s.id = :studentId")
    List<Task> findByStudentInTaskStudents(@Param("studentId") Long studentId);
    
}
