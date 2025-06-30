package com.hawkscheck.hawkscheck.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.model.User;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
    Optional<Team> findByMentor(User mentor);
}
