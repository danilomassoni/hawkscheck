package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.TaskRequestDTO;
import com.hawkscheck.hawkscheck.dto.TaskResponseDTO;
import com.hawkscheck.hawkscheck.dto.TaskStatusUpdateDTO;
import com.hawkscheck.hawkscheck.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<TaskResponseDTO> create(@RequestBody TaskRequestDTO dto, Principal principal) {
        return ResponseEntity.ok(taskService.create(dto, principal));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.ok(taskService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @GetMapping
    public List<TaskResponseDTO> listAll() {
        return taskService.listAll();
    }

    @GetMapping("/by-team/{teamId}")
    public ResponseEntity<List<TaskResponseDTO>> getByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(taskService.listByTeam(teamId));
    }


    @GetMapping("/my-tasks")
    @PreAuthorize("hasRole('STUDENT')")
    public List<TaskResponseDTO> getTasksForStudent(Principal principal) {
        return taskService.listTasksByStudent(principal);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Long id, @RequestBody @Valid TaskStatusUpdateDTO statusDTO, Principal principal) {
        taskService.updateStatus(id, statusDTO.getStatus(), principal);
        return ResponseEntity.noContent().build();
    }



}
