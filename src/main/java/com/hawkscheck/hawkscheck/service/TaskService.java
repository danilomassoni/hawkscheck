package com.hawkscheck.hawkscheck.service;

import com.hawkscheck.hawkscheck.dto.TaskRequestDTO;
import com.hawkscheck.hawkscheck.dto.TaskResponseDTO;
import com.hawkscheck.hawkscheck.model.*;
import com.hawkscheck.hawkscheck.repository.TaskRepository;
import com.hawkscheck.hawkscheck.repository.TeamRepository;
import com.hawkscheck.hawkscheck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public TaskResponseDTO create(TaskRequestDTO dto, Principal principal) {
        User mentor = userRepository.findByEmail(principal.getName()).orElseThrow();
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow();

        Set<User> students = dto.getStudentIds() != null
                ? dto.getStudentIds().stream()
                      .map(id -> userRepository.findById(id).orElseThrow())
                      .collect(Collectors.toSet())
                : Set.of();

        Task task = Task.builder()
                .title(dto.getTitle())
                .topic(dto.getTopic())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .mentor(mentor)
                .team(team)
                .students(students)
                .build();

        Task saved = taskRepository.save(task);
        return toDTO(saved);
    }

    public TaskResponseDTO update(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id).orElseThrow();
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow();

        Set<User> students = dto.getStudentIds() != null
                ? dto.getStudentIds().stream()
                      .map(sid -> userRepository.findById(sid).orElseThrow())
                      .collect(Collectors.toSet())
                : Set.of();

        task.setTitle(dto.getTitle());
        task.setTopic(dto.getTopic());
        task.setDescription(dto.getDescription());
        task.setStartDate(dto.getStartDate());
        task.setEndDate(dto.getEndDate());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());
        task.setStudents(students);
        task.setTeam(team);

        return toDTO(taskRepository.save(task));
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskResponseDTO getById(Long id) {
        return toDTO(taskRepository.findById(id).orElseThrow());
    }

    public List<TaskResponseDTO> listAll() {
        return taskRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private TaskResponseDTO toDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .topic(task.getTopic())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .mentorId(task.getMentor().getId())
                .mentorName(task.getMentor().getName())
                .teamId(task.getTeam().getId())
                .teamName(task.getTeam().getName())
                .studentIds(task.getStudents().stream().map(User::getId).collect(Collectors.toSet()))
                .build();
    }
}
