package com.resource.skillstracker.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.resource.skillstracker.dto.SkillDTO;
import com.resource.skillstracker.exception.ResourceNotFoundException;
import com.resource.skillstracker.model.Skill;
import com.resource.skillstracker.model.SkillLevel;
import com.resource.skillstracker.repository.SkillLevelRepository;
import com.resource.skillstracker.repository.SkillRepository;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillLevelRepository skillLevelRepository;

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }

    public SkillLevel addSkillLevel(SkillLevel skillLevel) {
        return skillLevelRepository.save(skillLevel);
    }
    
    public Page<Skill> getAllSkills(Pageable pageable) {
        return skillRepository.findAll(pageable);
    }
    public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        if (optionalSkill.isPresent()) {
            Skill skill = optionalSkill.get();
            skill.setName(skillDTO.getName());
            Skill updatedSkill = skillRepository.save(skill);
            return new SkillDTO(updatedSkill);
        } else {
            throw new ResourceNotFoundException("Skill not found with id " + id);
        }
    }
    
    public List<SkillDTO> addSkills(List<SkillDTO> skillDTOs) {
        List<Skill> skills = skillDTOs.stream()
            .map(this::convertToEntity)
            .collect(Collectors.toList());

        List<Skill> savedSkills = skillRepository.saveAll(skills);
        return savedSkills.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    // Convert DTO to Entity
    private Skill convertToEntity(SkillDTO skillDTO) {
        return new Skill(skillDTO.getName());
    }

    // Convert Entity to DTO
    private SkillDTO convertToDTO(Skill skill) {
        return new SkillDTO(skill.getId(), skill.getName());
    }
    
}