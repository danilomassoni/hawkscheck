package com.hawkscheck.hawkscheck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hawkscheck.hawkscheck.dto.UserRequestDTO;
import com.hawkscheck.hawkscheck.dto.UserResponseDTO;
import com.hawkscheck.hawkscheck.model.PaperEnum;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks 
    private UserService userService;

    private User user;
    private UserRequestDTO userRequest;

    @BeforeEach 
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("123456");
        user.setPaper(PaperEnum.ADMIN);

        userRequest = new UserRequestDTO();
        userRequest.setName("Test User");
        userRequest.setEmail("test@test.com");
        userRequest.setPassword("123456");
        userRequest.setPaper(PaperEnum.ADMIN);
        
    }

    @Test
    void testCreateUser() {
    
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponseDTO response = userService.createUser(userRequest);

    assertNotNull(response);
    assertEquals("Test User", response.getName());
    assertEquals("test@test.com", response.getEmail());
    assertEquals(PaperEnum.ADMIN, response.getPaper());

    verify(userRepository, times(1)).save(any(User.class)); 
}

    @Test
    void testFindAllUser() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDTO> users = userService.findAllUser();

        assertEquals(1, users.size());
        assertEquals("Test User", users.get(0).getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserIdFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserResponseDTO> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test User", result.get().getName());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<UserResponseDTO> result = userService.findById(2L);

        assertFalse(result.isPresent());

        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);

    }


}
