package com.resource.skillstracker.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.resource.skillstracker.model.Person;

public class PersonDTO {

    private Long id;
    private String name;
    private Set<SkillLevelDTO> skillLevels;

 // Default constructor
    public PersonDTO() {
    }

    // Constructor to convert Person entity to PersonDTO
    public PersonDTO(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.skillLevels = person.getSkillLevels().stream()
                                 .map(SkillLevelDTO::new)
                                 .collect(Collectors.toSet());
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SkillLevelDTO> getSkillLevels() {
        return skillLevels;
    }

    public void setSkillLevels(Set<SkillLevelDTO> skillLevels) {
        this.skillLevels = skillLevels;
    }
    
}
