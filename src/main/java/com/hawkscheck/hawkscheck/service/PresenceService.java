package com.hawkscheck.hawkscheck.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.model.Presence;
import com.hawkscheck.hawkscheck.model.StatusPresenceEnum;
import com.hawkscheck.hawkscheck.repository.PresenceRepository;

@Service
public class PresenceService {
    
    @Autowired
    private PresenceRepository presenceRepository;

    public Presence savePresence(Presence presence) {
        return presenceRepository.save(presence);
    }   

    public List<Presence> findAllPresences() {
        return presenceRepository.findAll();
    }

    public List<Presence> findByStudent(Long studentId) {
        return presenceRepository.findByStudentId(studentId);
    }

    public List<Presence> findByDate(LocalDate date) {
        return presenceRepository.findByDate(date);
    }

    public List<Presence> findByStatus(StatusPresenceEnum status) {
        return presenceRepository.findByStatusPresence(status);
    }

    public List<Presence> findByDateBetween(LocalDate start, LocalDate end) {
        return presenceRepository.findByDateBetween(start, end);
    }


    
}
