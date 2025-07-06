package com.hawkscheck.hawkscheck.repository;

import com.hawkscheck.hawkscheck.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
