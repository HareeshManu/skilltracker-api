package com.resource.skillstracker.controller;

import java.util.HashSet;
import java.util.Optional;
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

import com.resource.skillstracker.dto.PersonDTO;
import com.resource.skillstracker.dto.SkillLevelDTO;
import com.resource.skillstracker.model.Person;
import com.resource.skillstracker.model.Skill;
import com.resource.skillstracker.model.SkillLevel;
import com.resource.skillstracker.service.PersonService;
import com.resource.skillstracker.service.SkillLevelService;
import com.resource.skillstracker.service.SkillService;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillLevelService skillLevelService;

    // Create a Person
    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person = personService.save(person);
        PersonDTO result = convertToDTO(person);
        return ResponseEntity.ok(result);
    }
    // Get All People
    @GetMapping
    public ResponseEntity<Page<PersonDTO>> getAllPeople(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PersonDTO> people = personService.getAllPeople(pageable);
        return ResponseEntity.ok(people); // 200 OK
    }

    // Get a single Person
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        Optional<Person> personOpt = personService.findById(id);
        return personOpt.map(person -> ResponseEntity.ok(convertToDTO(person)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add Skill Level to Person
    @PostMapping("/{personId}/skills")
    public ResponseEntity<SkillLevelDTO> addSkillToPerson(@PathVariable Long personId, @RequestBody SkillLevelDTO skillLevelDTO) {
        Optional<Person> personOpt = personService.findById(personId);
        Optional<Skill> skillOpt = skillService.findById(skillLevelDTO.getSkillId());

        if (personOpt.isPresent() && skillOpt.isPresent()) {
            SkillLevel skillLevel = new SkillLevel();
            skillLevel.setPerson(personOpt.get());
            skillLevel.setSkill(skillOpt.get());
            skillLevel.setLevel(skillLevelDTO.getLevel());
            skillLevel = skillLevelService.save(skillLevel);
            SkillLevelDTO result = convertToDTO(skillLevel);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Delete a Person
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPerson = personService.updatePerson(id, personDTO);
        return ResponseEntity.ok(updatedPerson);
    }
    // Helper methods to convert entities to DTOs
    private PersonDTO convertToDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setSkillLevels(person.getSkillLevels() != null ? 
            person.getSkillLevels().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet()) : new HashSet<>());
        return dto;
    }

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