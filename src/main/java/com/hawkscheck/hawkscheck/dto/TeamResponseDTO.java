package com.hawkscheck.hawkscheck.dto;

public class TeamResponseDTO {

    private Long id;
    private String name;
    private String mentorName;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMentorName(String mentoNamer) {
        this.mentorName = mentorName;
    }
    
}
