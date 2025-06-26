package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.SkillDTO;
import upc.helpwave.entities.Skill;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceinterfaces.ISkillService;
import upc.helpwave.serviceimplements.JwtUserDetailsService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SkillController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISkillService skillService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldCallInsert() throws Exception {
        SkillDTO dto = new SkillDTO();
        dto.setSkillDesc("Programming");

        doNothing().when(skillService).insert(any(Skill.class));

        mockMvc.perform(post("/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldCallDelete() throws Exception {
        doNothing().when(skillService).delete(1);

        mockMvc.perform(delete("/skills/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void listId_shouldReturnSkillDTO() throws Exception {
        Skill skill = new Skill();
        skill.setIdSkill(1);
        skill.setSkillDesc("Programming");

        when(skillService.listId(1)).thenReturn(skill);

        mockMvc.perform(get("/skills/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSkill").value(1))
                .andExpect(jsonPath("$.skillDesc").value("Programming"));
    }

    @Test
    void list_shouldReturnListOfSkills() throws Exception {
        Skill skill1 = new Skill();
        skill1.setIdSkill(1);
        skill1.setSkillDesc("Programming");

        Skill skill2 = new Skill();
        skill2.setIdSkill(2);
        skill2.setSkillDesc("Communication");

        when(skillService.list()).thenReturn(List.of(skill1, skill2));

        mockMvc.perform(get("/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idSkill").value(1))
                .andExpect(jsonPath("$[0].skillDesc").value("Programming"))
                .andExpect(jsonPath("$[1].idSkill").value(2))
                .andExpect(jsonPath("$[1].skillDesc").value("Communication"));
    }

    @Test
    void update_shouldCallInsert() throws Exception {
        SkillDTO dto = new SkillDTO();
        dto.setIdSkill(1);
        dto.setSkillDesc("Programming");

        doNothing().when(skillService).insert(any(Skill.class));

        mockMvc.perform(put("/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
