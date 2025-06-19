package com.hawkscheck.hawkscheck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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

    @Test
void testCreateUser() {
    UserRequestDTO request = new UserRequestDTO();
    request.setName("Test User");
    request.setEmail("test@test.com");
    request.setPassword("123456");
    request.setPaper(PaperEnum.ADMIN);

    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setPaper(request.getPaper());

    when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponseDTO created = userService.createUser(request);

    assertNotNull(created);
    assertEquals("Test User", created.getName());
    assertEquals("test@test.com", created.getEmail());
    verify(userRepository, times(1)).save(any(User.class)); // Melhor usar any()
}

    @Test
    void testGetByEmailFound() {
        User user = new User();
        user.setEmail("test@test.com");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getByEmail("test@test.com");

        assertTrue(result.isPresent());
        assertEquals("test@test.com", result.get().getEmail());
    }

    @Test
    void testGetByEmailNotFound() {
        when(userRepository.findByEmail("non-existent@gmail.com")).thenReturn(Optional.empty());

        Optional<User> result = userService.getByEmail("non-existent@gmai.com");
        
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
            new User("User 1", "user1@test.com", "123", PaperEnum.ADMIN),
            new User("User 2", "test2@test.com", "abc", PaperEnum.MENTOR)
        );
    }




}
