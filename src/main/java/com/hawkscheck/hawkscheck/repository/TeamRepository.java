package com.hawkscheck.hawkscheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hawkscheck.hawkscheck.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    
}
