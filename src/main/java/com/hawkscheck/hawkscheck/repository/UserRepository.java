package com.hawkscheck.hawkscheck.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hawkscheck.hawkscheck.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
