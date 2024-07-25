package com.resource.skillstracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resource.skillstracker.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
