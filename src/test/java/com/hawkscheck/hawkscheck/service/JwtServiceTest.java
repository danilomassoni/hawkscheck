package com.hawkscheck.hawkscheck.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.hawkscheck.hawkscheck.model.PaperEnum;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.security.JwtService;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    
    private JwtService jwtService;
    private User testUser;


    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        String fakeSecret = "125SD547856Sa51s5dd5e9w51SASS12354";
        ReflectionTestUtils.setField(jwtService, "secretKey", fakeSecret);
    
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@test.com");
        testUser.setPassword("123456");
        testUser.setPaper(PaperEnum.ADMIN);
    }

    @Test
    void testShouldGeneratedValidToken() {
        String token = jwtService.generateToken(testUser, testUser.getPaper().toString());

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testSgouldExtractUsernameFromToken() {
        String token = jwtService.generateToken(testUser, testUser.getPaper().toString());

        String username = jwtService.extractUsername(token);

        assertEquals(testUser.getEmail(), username);
    }

    @Test
    void testShouldValidateTokenSuccessfully() {
        String token = jwtService.generateToken(testUser, testUser.getPaper().toString());

        boolean isValid = jwtService.isTokenValid(token, testUser);

        assertTrue(isValid);
    }


}
