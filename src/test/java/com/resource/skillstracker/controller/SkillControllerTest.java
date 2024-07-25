package com.resource.skillstracker.controller;

import com.resource.skillstracker.dto.SkillDTO;
import com.resource.skillstracker.model.Skill;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillControllerTest {

    @Mock
    private SkillService skillService;

    @InjectMocks
    private SkillController skillController;

    private Skill skill;
    private SkillDTO skillDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        skill = new Skill();
        skill.setId(1L);
        skill.setName("Java");

        skillDTO = new SkillDTO();
        skillDTO.setId(1L);
        skillDTO.setName("Java");
    }

    @Test
    void testGetAllSkills() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Skill> skillPage = new PageImpl<>(Collections.singletonList(skill));

        when(skillService.getAllSkills(pageable)).thenReturn(skillPage);

        ResponseEntity<List<SkillDTO>> response = skillController.getAllSkills(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(skillService, times(1)).getAllSkills(pageable);
    }

    @Test
    void testAddSkills() {
        List<SkillDTO> skillDTOs = Arrays.asList(skillDTO);
        when(skillService.addSkills(anyList())).thenReturn(skillDTOs);

        ResponseEntity<List<SkillDTO>> response = skillController.addSkills(skillDTOs);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(skillService, times(1)).addSkills(anyList());
    }

    @Test
    void testUpdateSkill() {
        when(skillService.updateSkill(anyLong(), any(SkillDTO.class))).thenReturn(skillDTO);

        ResponseEntity<SkillDTO> response = skillController.updateSkill(1L, skillDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(skillDTO.getName(), response.getBody().getName());
        verify(skillService, times(1)).updateSkill(anyLong(), any(SkillDTO.class));
    }

    @Test
    void testDeleteSkill() {
        doNothing().when(skillService).deleteSkill(anyLong());

        ResponseEntity<Void> response = skillController.deleteSkill(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(skillService, times(1)).deleteSkill(anyLong());
    }
}
