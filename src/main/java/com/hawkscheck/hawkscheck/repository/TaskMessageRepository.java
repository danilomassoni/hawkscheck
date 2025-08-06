package com.hawkscheck.hawkscheck.repository;

import com.hawkscheck.hawkscheck.model.TaskMessage;
import com.hawkscheck.hawkscheck.model.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TaskMessageRepository extends JpaRepository<TaskMessage, Long> {
    Page<TaskMessage> findByTask(Task task, Pageable pageable);
}