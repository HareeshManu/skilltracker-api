package com.resource.skillstracker.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Default constructor
    public Skill() {
    }

    // Constructor with parameters
    public Skill(String name) {
        this.name = name;
    }
    
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<SkillLevel> skillLevels = new HashSet<>();

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

    public Set<SkillLevel> getSkillLevels() {
        return skillLevels;
    }

    public void setSkillLevels(Set<SkillLevel> skillLevels) {
        this.skillLevels = skillLevels;
    }
}