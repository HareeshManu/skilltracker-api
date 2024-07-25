package com.resource.skillstracker.controller;

import com.resource.skillstracker.dto.PersonDTO;
import com.resource.skillstracker.dto.SkillLevelDTO;
import com.resource.skillstracker.model.Person;
import com.resource.skillstracker.model.Skill;
import com.resource.skillstracker.model.SkillLevel;
import com.resource.skillstracker.service.PersonService;
import com.resource.skillstracker.service.SkillLevelService;
import com.resource.skillstracker.service.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private PersonService personService;

    @Mock
    private SkillService skillService;

    @Mock
    private SkillLevelService skillLevelService;

    @InjectMocks
    private PersonController personController;

    private Person person;
    private PersonDTO personDTO;
    private Skill skill;
    private SkillLevel skillLevel;
    private SkillLevelDTO skillLevelDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        person = new Person();
        person.setId(1L);
        person.setName("John Doe");

        personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setName("John Doe");

        skill = new Skill();
        skill.setId(1L);
        skill.setName("Java");

        skillLevel = new SkillLevel();
        skillLevel.setId(1L);
        skillLevel.setPerson(person);
        skillLevel.setSkill(skill);
        skillLevel.setLevel(SkillLevel.Level.EXPERT);

        skillLevelDTO = new SkillLevelDTO();
        skillLevelDTO.setId(1L);
        skillLevelDTO.setPersonId(1L);
        skillLevelDTO.setSkillId(1L);
        skillLevelDTO.setLevel(SkillLevel.Level.EXPERT);

        Set<SkillLevel> skillLevels = new HashSet<>();
        skillLevels.add(skillLevel);
        person.setSkillLevels(skillLevels);

        Set<SkillLevelDTO> skillLevelDTOs = new HashSet<>();
        skillLevelDTOs.add(skillLevelDTO);
        personDTO.setSkillLevels(skillLevelDTOs);
    }

    @Test
    void testCreatePerson() {
        when(personService.save(any(Person.class))).thenReturn(person);

        ResponseEntity<PersonDTO> response = personController.createPerson(personDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDTO.getName(), response.getBody().getName());
        verify(personService, times(1)).save(any(Person.class));
    }

    @Test
    void testGetAllPeople() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PersonDTO> page = new PageImpl<>(Collections.singletonList(personDTO));

        when(personService.getAllPeople(pageable)).thenReturn(page);

        ResponseEntity<Page<PersonDTO>> response = personController.getAllPeople(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(personService, times(1)).getAllPeople(pageable);
    }

    @Test
    void testGetPersonById() {
        when(personService.findById(anyLong())).thenReturn(Optional.of(person));

        ResponseEntity<PersonDTO> response = personController.getPersonById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDTO.getName(), response.getBody().getName());
        verify(personService, times(1)).findById(1L);
    }

    @Test
    void testAddSkillToPerson() {
        when(personService.findById(anyLong())).thenReturn(Optional.of(person));
        when(skillService.findById(anyLong())).thenReturn(Optional.of(skill));
        when(skillLevelService.save(any(SkillLevel.class))).thenReturn(skillLevel);

        ResponseEntity<SkillLevelDTO> response = personController.addSkillToPerson(1L, skillLevelDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(skillLevelDTO.getLevel(), response.getBody().getLevel());
        verify(personService, times(1)).findById(1L);
        verify(skillService, times(1)).findById(1L);
        verify(skillLevelService, times(1)).save(any(SkillLevel.class));
    }

    @Test
    void testDeletePerson() {
        doNothing().when(personService).deleteById(anyLong());

        ResponseEntity<Void> response = personController.deletePerson(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personService, times(1)).deleteById(1L);
    }

    @Test
    void testUpdatePerson() {
        when(personService.updatePerson(anyLong(), any(PersonDTO.class))).thenReturn(personDTO);

        ResponseEntity<PersonDTO> response = personController.updatePerson(1L, personDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDTO.getName(), response.getBody().getName());
        verify(personService, times(1)).updatePerson(anyLong(), any(PersonDTO.class));
    }
}
