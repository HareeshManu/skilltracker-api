package com.resource.skillstracker.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resource.skillstracker.dto.SkillLevelDTO;
import com.resource.skillstracker.exception.ResourceNotFoundException;
import com.resource.skillstracker.model.Person;
import com.resource.skillstracker.model.Skill;
import com.resource.skillstracker.model.SkillLevel;
import com.resource.skillstracker.repository.PersonRepository;
import com.resource.skillstracker.repository.SkillLevelRepository;
import com.resource.skillstracker.repository.SkillRepository;

import java.util.Optional;

@Service
public class SkillLevelService {

    @Autowired
    private SkillLevelRepository skillLevelRepository;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SkillRepository skillRepository;

    public SkillLevel save(SkillLevel skillLevel) {
        return skillLevelRepository.save(skillLevel);
    }

    public Optional<SkillLevel> findById(Long id) {
        return skillLevelRepository.findById(id);
    }

    public void deleteById(Long id) {
        skillLevelRepository.deleteById(id);
    }

    public Iterable<SkillLevel> findAll() {
        return skillLevelRepository.findAll();
    }
    public SkillLevelDTO updateSkillLevel(Long personId, Long skillLevelId, SkillLevelDTO skillLevelDTO) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        Optional<SkillLevel> optionalSkillLevel = skillLevelRepository.findById(skillLevelId);

        if (optionalPerson.isPresent() && optionalSkillLevel.isPresent()) {
            SkillLevel skillLevel = optionalSkillLevel.get();
            skillLevel.setLevel(skillLevelDTO.getLevel());
            if (skillLevelDTO.getSkillId() != null) {
                Skill skill = skillRepository.findById(skillLevelDTO.getSkillId())
                    .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id " + skillLevelDTO.getSkillId()));
                skillLevel.setSkill(skill);
            }
            SkillLevel updatedSkillLevel = skillLevelRepository.save(skillLevel);
            return new SkillLevelDTO(updatedSkillLevel);
        } else {
            throw new ResourceNotFoundException("Person or SkillLevel not found with given ids");
        }
    }
}