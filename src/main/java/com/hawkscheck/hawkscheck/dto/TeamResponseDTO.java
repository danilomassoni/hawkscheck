package com.hawkscheck.hawkscheck.dto;

public class TeamResponseDTO {

    private Long id;
    private String name;
    private String mentorName;


    public TeamResponseDTO(Long id, String name, String mentorName) {
        this.id = id;
        this.name = name;
        this.mentorName = mentorName;
    }

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

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }
}