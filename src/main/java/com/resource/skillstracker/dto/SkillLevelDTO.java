package com.resource.skillstracker.dto;

import com.resource.skillstracker.model.SkillLevel;
import com.resource.skillstracker.model.SkillLevel.Level;

public class SkillLevelDTO {
    private Long id;
    private Long personId;
    private Long skillId;
    private String skillName;
    private SkillLevel.Level level;

    // Default constructor
    public SkillLevelDTO() {}

    // Constructor to convert SkillLevel entity to SkillLevelDTO
    public SkillLevelDTO(SkillLevel skillLevel) {
        this.id = skillLevel.getId();
        this.personId = skillLevel.getPerson().getId();
        this.skillId = skillLevel.getSkill().getId();
        this.skillName = skillLevel.getSkill().getName();
        this.level = skillLevel.getLevel();
    }
    
    public SkillLevelDTO(Long id, Long skillId, Level level) {
        this.id = id;
        this.skillId = skillId;
        this.level = level;
    }
    
    public SkillLevelDTO(Long id, Long personId, Long skillId, String skillName, SkillLevel.Level level) {
        this.id = id;
        this.personId = personId;
        this.skillId = skillId;
        this.skillName = skillName;
        this.level = level;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }


    public SkillLevel.Level getLevel() {
        return level;
    }

    public void setLevel(SkillLevel.Level level) {
        this.level = level;
    }
}