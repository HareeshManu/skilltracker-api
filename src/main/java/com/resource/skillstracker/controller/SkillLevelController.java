package com.resource.skillstracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resource.skillstracker.dto.SkillLevelDTO;
import com.resource.skillstracker.service.SkillLevelService;
@RestController
@RequestMapping("/api/people/{personId}/skills")
public class SkillLevelController {

    @Autowired
    private SkillLevelService skillLevelService;

    @PutMapping("/{skillLevelId}")
    public ResponseEntity<SkillLevelDTO> updateSkillLevel(
        @PathVariable Long personId,
        @PathVariable Long skillLevelId,
        @RequestBody SkillLevelDTO skillLevelDTO) {
        SkillLevelDTO updatedSkillLevel = skillLevelService.updateSkillLevel(personId, skillLevelId, skillLevelDTO);
        return ResponseEntity.ok(updatedSkillLevel);
    }
}