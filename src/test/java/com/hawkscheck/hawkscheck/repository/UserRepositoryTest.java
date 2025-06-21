package com.hawkscheck.hawkscheck.repository;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hawkscheck.hawkscheck.model.PaperEnum;
import com.hawkscheck.hawkscheck.model.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository; 

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test");
        user.setEmail("test@test.com");
        user.setPassword("123456");
        user.setPaper(PaperEnum.ADMIN);

        userRepository.save(user);
    }

    @Test
    @DisplayName("Find user by email")
    void testFindByEmailTest() {
        Optional<User> found = userRepository.findByEmail("test@test.com");

        assertTrue(found.isPresent());
        assertEquals("Test", found.get().getName());
    }

    @Test
    @DisplayName("Find returns ture if email exists")
    void testExistsByEmail() {
        assertTrue(userRepository.existsByEmail("test@test.com"));
    }

    @Test
    @DisplayName("Returns false if email does not exist")
    void testNotExistsByEmail() {
        assertFalse(userRepository.existsByEmail("notexiststest@test.com"));
    }

    
}
