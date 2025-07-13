package com.hawkscheck.hawkscheck.repository;

import com.hawkscheck.hawkscheck.model.Task;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStudentsContaining(User student);
    List<Task> findByTeam(Team team);
}
