package com.hawkscheck.hawkscheck.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawkscheck.hawkscheck.dto.UserRequestDTO;
import com.hawkscheck.hawkscheck.dto.UserResponseDTO;
import com.hawkscheck.hawkscheck.model.PaperEnum;
import com.hawkscheck.hawkscheck.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDTO request; 
    private UserResponseDTO response;

    @BeforeEach
    void setUp() {
        request = new UserRequestDTO("Test", "test@test.com", "123456", PaperEnum.ADMIN);
        response = new UserResponseDTO(1L, "Test", "test@test.com", PaperEnum.ADMIN);
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(UserRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Test"))
            .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testFindAllUser() throws Exception {
        when(userService.findAllUser()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testFindByIdFound() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(response));

        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test 
    @WithMockUser(roles = "ADMIN")
    void testFindByIdNotFound() throws Exception {
        when(userService.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/2"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN") 
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
            .andExpect(status().isNoContent());
    }









}
