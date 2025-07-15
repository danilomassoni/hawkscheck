package com.hawkscheck.hawkscheck.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hawkscheck.hawkscheck.dto.UserRequestDTO;
import com.hawkscheck.hawkscheck.dto.UserResponseDTO;
import com.hawkscheck.hawkscheck.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto, Principal principal) {
        return ResponseEntity.ok(userService.createUser(dto, principal));
    }

    @Operation(summary = "Listar todos os usuários")
    @GetMapping 
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUser());
    }

    @Operation(summary = "Buscar usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir usuário por ID")
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os estudantes")
    @GetMapping("/students")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<List<UserResponseDTO>> findAllStudents() {
        return ResponseEntity.ok(userService.findAllStudents());
    }

    @Operation(summary = "Cadastrar novo estudante vinculado ao mentor logado")
    @PostMapping("/students")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<UserResponseDTO> createStudent(@RequestBody UserRequestDTO dto, Principal principal) {
        return ResponseEntity.ok(userService.createStudent(dto, principal));
    }

    @Operation(summary = "Listar alunos vinculados ao mentor logado")
    @GetMapping("/my-students")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<List<UserResponseDTO>> getMyStudents(Principal principal) {
        return ResponseEntity.ok(userService.findStudentsByMentor(principal.getName()));
    }




}
