package com.resource.skillstracker.service;

import com.resource.skillstracker.model.Skill;
import com.resource.skillstracker.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    @Test
    public void testSaveSkill() {
        Skill skill = new Skill();
        skill.setName("Java");

        when(skillRepository.save(skill)).thenReturn(skill);

        Skill savedSkill = skillService.save(skill);

        assertEquals("Java", savedSkill.getName());
        verify(skillRepository, times(1)).save(skill);
    }
}
