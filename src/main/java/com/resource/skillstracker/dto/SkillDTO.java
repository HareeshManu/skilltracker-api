package com.resource.skillstracker.dto;
import java.util.Set;
import java.util.stream.Collectors;

import com.resource.skillstracker.model.Skill;

public class SkillDTO {

    private Long id;
    private String name;
    private Set<SkillLevelDTO> skillLevels;
    
    // Default constructor
    public SkillDTO() {
    }

    // Constructor to convert Skill entity to SkillDTO
    public SkillDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
        this.skillLevels = skill.getSkillLevels().stream()
                                .map(SkillLevelDTO::new)
                                .collect(Collectors.toSet());
    }
    public SkillDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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