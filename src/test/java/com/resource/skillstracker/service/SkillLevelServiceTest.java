package com.resource.skillstracker.service;

import com.resource.skillstracker.dto.SkillLevelDTO;
import com.resource.skillstracker.exception.ResourceNotFoundException;
import com.resource.skillstracker.model.Person;
import com.resource.skillstracker.model.Skill;
import com.resource.skillstracker.model.SkillLevel;
import com.resource.skillstracker.repository.PersonRepository;
import com.resource.skillstracker.repository.SkillLevelRepository;
import com.resource.skillstracker.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillLevelServiceTest {

    @Mock
    private SkillLevelRepository skillLevelRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillLevelService skillLevelService;

    private SkillLevel skillLevel;
    private Person person;
    private Skill skill;
    private SkillLevelDTO skillLevelDTO;

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setId(1L);
        person.setName("John Doe");

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
    }

    @Test
    public void testSaveSkillLevel() {
        when(skillLevelRepository.save(any(SkillLevel.class))).thenReturn(skillLevel);

        SkillLevel savedSkillLevel = skillLevelService.save(skillLevel);

        assertNotNull(savedSkillLevel);
        assertEquals(SkillLevel.Level.EXPERT, savedSkillLevel.getLevel());
        verify(skillLevelRepository, times(1)).save(skillLevel);
    }

    @Test
    public void testFindById() {
        when(skillLevelRepository.findById(1L)).thenReturn(Optional.of(skillLevel));

        Optional<SkillLevel> foundSkillLevel = skillLevelService.findById(1L);

        assertTrue(foundSkillLevel.isPresent());
        assertEquals(1L, foundSkillLevel.get().getId());
        verify(skillLevelRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(skillLevelRepository).deleteById(1L);

        skillLevelService.deleteById(1L);

        verify(skillLevelRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateSkillLevel() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(skillLevelRepository.findById(1L)).thenReturn(Optional.of(skillLevel));
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(skillLevelRepository.save(any(SkillLevel.class))).thenReturn(skillLevel);

        SkillLevelDTO updatedSkillLevelDTO = skillLevelService.updateSkillLevel(1L, 1L, skillLevelDTO);

        assertNotNull(updatedSkillLevelDTO);
        assertEquals(SkillLevel.Level.EXPERT, updatedSkillLevelDTO.getLevel());
        verify(skillLevelRepository, times(1)).save(skillLevel);
    }

    @Test
    public void testUpdateSkillLevel_ThrowsException() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            skillLevelService.updateSkillLevel(1L, 1L, skillLevelDTO);
        });

        verify(skillLevelRepository, times(0)).save(any(SkillLevel.class));
    }
}
