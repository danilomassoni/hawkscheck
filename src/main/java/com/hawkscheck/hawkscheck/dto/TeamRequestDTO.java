package com.hawkscheck.hawkscheck.dto;

public class TeamRequestDTO {

    private String name; 

    public TeamRequestDTO() {}

    public TeamRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
