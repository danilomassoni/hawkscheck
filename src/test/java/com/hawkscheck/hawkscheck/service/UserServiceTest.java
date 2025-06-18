package com.hawkscheck.hawkscheck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        User user = new User();

        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("123456");
        user.setPaper(PaperEnum.ADMIN);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User created = userService.createUser(user);

        assertNotNull(created);
        assertEquals("Danilo", created.getName());
        assertEquals("danilo@hawks.com", created.getEmail());
        verify(userRepository, times(1)).save(user);
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
