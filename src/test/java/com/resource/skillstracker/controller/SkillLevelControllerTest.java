package com.resource.skillstracker.controller;

import com.resource.skillstracker.dto.SkillLevelDTO;
import com.resource.skillstracker.exception.ResourceNotFoundException;
import com.resource.skillstracker.model.SkillLevel;
import com.resource.skillstracker.service.SkillLevelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillLevelControllerTest {

    @Mock
    private SkillLevelService skillLevelService;

    @InjectMocks
    private SkillLevelController skillLevelController;

    private SkillLevelDTO skillLevelDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        skillLevelDTO = new SkillLevelDTO();
        skillLevelDTO.setId(1L);
        skillLevelDTO.setPersonId(1L);
        skillLevelDTO.setSkillId(1L);
        skillLevelDTO.setSkillName("Java");
        skillLevelDTO.setLevel(SkillLevel.Level.EXPERT); // Use the enum constant here
    }

    @Test
    void testUpdateSkillLevel_Success() {
        when(skillLevelService.updateSkillLevel(anyLong(), anyLong(), any(SkillLevelDTO.class)))
                .thenReturn(skillLevelDTO);

        ResponseEntity<SkillLevelDTO> response = skillLevelController.updateSkillLevel(1L, 1L, skillLevelDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(skillLevelDTO, response.getBody());
        verify(skillLevelService, times(1))
                .updateSkillLevel(anyLong(), anyLong(), any(SkillLevelDTO.class));
    }

}
