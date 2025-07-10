package com.hawkscheck.hawkscheck.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponseDTO {

    private Long id;
    private String name;
    private String mentorName;
    private Long mentorId;


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