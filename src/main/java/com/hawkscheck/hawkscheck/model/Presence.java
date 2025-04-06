package com.hawkscheck.hawkscheck.model;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_presence")
@Getter
@Setter
public class Presence {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private LocalDate date; //date of the presence

    @Enumerated(EnumType.STRING)
    private StatusPresenceEnum statusPresence; // Presence or Lack

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user; 
}
