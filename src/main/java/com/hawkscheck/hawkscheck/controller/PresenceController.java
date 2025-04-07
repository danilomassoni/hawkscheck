package com.hawkscheck.hawkscheck.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hawkscheck.hawkscheck.model.Presence;
import com.hawkscheck.hawkscheck.model.StatusPresenceEnum;
import com.hawkscheck.hawkscheck.service.PresenceService;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    @PostMapping 
    public ResponseEntity<Presence> registerPresence(@RequestBody Presence presence) {
        Presence saved = presenceService.savePresence(presence);
        return ResponseEntity.ok(saved);
    }

    @GetMapping  
    public ResponseEntity<List<Presence>> listAll() {
        return ResponseEntity.ok(presenceService.findAllPresences());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Presence>> byStudent(@PathVariable Long id) {
        return ResponseEntity.ok(presenceService.findByStudent(id));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Presence>> byDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(presenceService.findByDate(date));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Presence>> byStatus(@PathVariable StatusPresenceEnum status) {
        return ResponseEntity.ok(presenceService.findByStatus(status));
    }
    
}
