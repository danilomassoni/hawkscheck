package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.TaskMessageRequestDTO;
import com.hawkscheck.hawkscheck.dto.TaskMessageResponseDTO;
import com.hawkscheck.hawkscheck.service.TaskMessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class TaskMessageController {

    private final TaskMessageService taskMessageService;

    @PostMapping("/{taskId}/messages")
    public ResponseEntity<TaskMessageResponseDTO> addMessage(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskMessageRequestDTO dto,
            Principal principal) {

        TaskMessageResponseDTO response = taskMessageService.addMessage(taskId, dto, principal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/task/{taskId}")
    public Page<TaskMessageResponseDTO> getMessagesByTask(
        @PathVariable Long taskId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    return taskMessageService.getMessagesByTask(taskId, page, size);
    }
}