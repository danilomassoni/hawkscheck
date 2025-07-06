package com.hawkscheck.hawkscheck.service;

import com.hawkscheck.hawkscheck.dto.TaskRequestDTO;
import com.hawkscheck.hawkscheck.dto.TaskResponseDTO;
import com.hawkscheck.hawkscheck.model.Task;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.TaskRepository;
import com.hawkscheck.hawkscheck.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.Set;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private TaskResponseDTO convertToDTO(Task task) {
    return TaskResponseDTO.builder()
            .id(task.getId())
            .title(task.getTitle())
            .topic(task.getTopic())
            .description(task.getDescription())
            .startDate(task.getStartDate())
            .endDate(task.getEndDate())
            .priority(task.getPriority())
            .status(task.getStatus())
            .mentorName(task.getMentor().getName())
            .assignedStudents(
                    task.getStudents().stream()
                        .map(User::getName)
                        .collect(Collectors.toList())
            )
            .build();
}


    public TaskResponseDTO create(TaskRequestDTO taskRequest, Principal principal) {
    User mentor = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("Mentor não encontrado"));

    Set<User> students = taskRequest.getStudentIds().stream()
            .map(id -> userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aluno com ID " + id + " não encontrado")))
            .collect(Collectors.toSet());

    Task task = Task.builder()
            .title(taskRequest.getTitle())
            .topic(taskRequest.getTopic())
            .description(taskRequest.getDescription())
            .startDate(taskRequest.getStartDate())
            .endDate(taskRequest.getEndDate())
            .priority(taskRequest.getPriority())
            .status(taskRequest.getStatus())
            .mentor(mentor)
            .students(students)
            .build();

    taskRepository.save(task);
    return convertToDTO(task);
}

    public TaskResponseDTO update(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(dto.getTitle());
        task.setTopic(dto.getTopic());
        task.setDescription(dto.getDescription());
        task.setStartDate(dto.getStartDate());
        task.setEndDate(dto.getEndDate());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());

        List<User> students = userRepository.findAllById(dto.getStudentIds());
        task.setStudents(new HashSet<>(students));

        taskRepository.save(task);
        return toResponseDTO(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskResponseDTO> listAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO getById(Long id) {
        return taskRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .topic(task.getTopic())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .mentorName(task.getMentor() != null ? task.getMentor().getName() : null)
                .assignedStudents(
                    task.getStudents()
                        .stream()
                        .map(User::getName)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
