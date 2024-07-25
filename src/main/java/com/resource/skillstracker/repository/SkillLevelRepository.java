package com.resource.skillstracker.repository;

import com.resource.skillstracker.model.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillLevelRepository extends JpaRepository<SkillLevel, Long> {
}