package com.hawkscheck.hawkscheck.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.hawkscheck.hawkscheck.model.PaperEnum;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByTeam(Team team);
    List<User> findByPaper(PaperEnum paper);
    List<User> findByPaperAndMentorEmail(PaperEnum paper, String mentorEmail);
    

}
