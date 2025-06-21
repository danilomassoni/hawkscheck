package com.hawkscheck.hawkscheck.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hawkscheck.hawkscheck.dto.AuthRequestDTO;
import com.hawkscheck.hawkscheck.dto.AuthResponseDTO;
import com.hawkscheck.hawkscheck.model.PaperEnum;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.UserRepository;
import com.hawkscheck.hawkscheck.security.JwtService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    
    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock 
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock 
    private Authentication authentication;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@test.com");
        testUser.setPassword("123456");
        testUser.setPaper(PaperEnum.ADMIN);

    }

    @Test
    void testShouldAuthenticateSuccessfully() {
        AuthRequestDTO request = new AuthRequestDTO("test@test.com", "123456");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser,"ADMIN")).thenReturn("fake-jwt-token");

        AuthResponseDTO response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
        assertEquals("Test User", response.getName());
        assertEquals(PaperEnum.ADMIN, response.getPaperEnum()); 

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, times(1)).generateToken(testUser, "ADMIN");

            
    }

    @Test
    void testShouldThrowExceptionWhenUserNotFound() {
        AuthRequestDTO request = new AuthRequestDTO("notfound@test.com", "wrongpass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            authService.authenticate(request);
        });

        verify(userRepository, times(1)).findByEmail(request.getEmail());


    }

}
