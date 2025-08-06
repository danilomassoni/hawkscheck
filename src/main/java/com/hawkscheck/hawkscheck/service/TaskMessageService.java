package com.hawkscheck.hawkscheck.service;

import com.hawkscheck.hawkscheck.dto.TaskMessageRequestDTO;
import com.hawkscheck.hawkscheck.dto.TaskMessageResponseDTO;
import com.hawkscheck.hawkscheck.model.Task;
import com.hawkscheck.hawkscheck.model.TaskMessage;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.TaskMessageRepository;
import com.hawkscheck.hawkscheck.repository.TaskRepository;
import com.hawkscheck.hawkscheck.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.PageRequest;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskMessageService {

    private final TaskMessageRepository taskMessageRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMessageRepository messageRepository;

    public TaskMessageResponseDTO addMessage(Long taskId, TaskMessageRequestDTO dto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("Author not found"));

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        TaskMessage message = TaskMessage.builder()
            .content(dto.getContent())
            .task(task)
            .author(user)
            .timestamp(LocalDateTime.now())
            .build();

        messageRepository.save(message);

        return TaskMessageResponseDTO.builder()
            .id(message.getId())
            .content(message.getContent())
            .authorName(user.getName())
            .authorId(user.getId())
            .timestamp(message.getTimestamp())
            .build();
    }


    public Page<TaskMessageResponseDTO> getMessagesByTask(Long taskId, int page, int size) {
    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());

    Page<TaskMessage> messagePage = taskMessageRepository.findByTask(task, pageable);

    return messagePage.map(this::toDTO);
}

    private TaskMessageResponseDTO toDTO(TaskMessage message) {
        return TaskMessageResponseDTO.builder()
                .id(message.getId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .authorId(message.getAuthor().getId())
                .authorName(message.getAuthor().getName())
                .build();
    }
}