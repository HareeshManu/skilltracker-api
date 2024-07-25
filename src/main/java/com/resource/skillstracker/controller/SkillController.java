package com.resource.skillstracker.controller;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resource.skillstracker.dto.SkillDTO;
import com.resource.skillstracker.dto.SkillLevelDTO;
import com.resource.skillstracker.model.Skill;
import com.resource.skillstracker.model.SkillLevel;
import com.resource.skillstracker.service.SkillService;
@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    // Get a Single Skill
    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Skill> skillPage = skillService.getAllSkills(pageable);
        List<SkillDTO> skillDTOs = skillPage.stream()
                .map(SkillDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(skillDTOs);
    }
    
    @PostMapping
    public ResponseEntity<List<SkillDTO>> addSkills(@RequestBody List<SkillDTO> skillDTOs) {
        List<SkillDTO> createdSkills = skillService.addSkills(skillDTOs);
        return ResponseEntity.ok(createdSkills); // 200 OK
    }
    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable Long id, @RequestBody SkillDTO skillDTO) {
        SkillDTO updatedSkill = skillService.updateSkill(id, skillDTO);
        return ResponseEntity.ok(updatedSkill);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    
    // Helper method to convert Skill entity to SkillDTO
    private SkillDTO convertToDTO(Skill skill) {
        SkillDTO dto = new SkillDTO();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        dto.setSkillLevels(skill.getSkillLevels() != null ? 
            skill.getSkillLevels().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet()) : new HashSet<>());
        return dto;
    }

    // Helper method to convert SkillLevel entity to SkillLevelDTO
    private SkillLevelDTO convertToDTO(SkillLevel skillLevel) {
        SkillLevelDTO dto = new SkillLevelDTO();
        dto.setId(skillLevel.getId());
        dto.setPersonId(skillLevel.getPerson().getId());
        dto.setSkillId(skillLevel.getSkill().getId());
        dto.setSkillName(skillLevel.getSkill().getName());
        dto.setLevel(skillLevel.getLevel());
        return dto;
    }
}