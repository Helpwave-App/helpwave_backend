package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.SkillProfileDTO;
import upc.helpwave.dtos.SkillProfileListDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.SkillRepository;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.ISkillProfileService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SkillProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SkillProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISkillProfileService skillProfileService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private SkillRepository skillRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        when(modelMapper.map(any(SkillProfileDTO.class), eq(SkillProfile.class))).thenAnswer(invocation -> {
            SkillProfileDTO dto = invocation.getArgument(0);
            SkillProfile sp = new SkillProfile();
            sp.setIdSkillProfile(dto.getIdSkillProfile());

            Profile profile = new Profile();
            profile.setIdProfile(dto.getIdProfile());
            sp.setProfile(profile);

            Skill skill = new Skill();
            skill.setIdSkill(dto.getIdSkill());
            sp.setSkill(skill);

            return sp;
        });

        when(modelMapper.map(any(SkillProfile.class), eq(SkillProfileDTO.class))).thenAnswer(invocation -> {
            SkillProfile sp = invocation.getArgument(0);
            SkillProfileDTO dto = new SkillProfileDTO();
            dto.setIdSkillProfile(sp.getIdSkillProfile());
            if (sp.getProfile() != null) {
                dto.setIdProfile(sp.getProfile().getIdProfile());
            }
            if (sp.getSkill() != null) {
                dto.setIdSkill(sp.getSkill().getIdSkill());
            }
            return dto;
        });
    }

    @Test
    void registerBatch_shouldInsertAll() throws Exception {
        SkillProfileListDTO dto = new SkillProfileListDTO();
        dto.setIdProfile(1);
        dto.setSkillIds(List.of(10, 20));

        Profile profile = new Profile();
        profile.setIdProfile(1);
        Skill skill1 = new Skill();
        skill1.setIdSkill(10);
        Skill skill2 = new Skill();
        skill2.setIdSkill(20);

        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(skillRepository.findById(10)).thenReturn(Optional.of(skill1));
        when(skillRepository.findById(20)).thenReturn(Optional.of(skill2));

        doNothing().when(skillProfileService).insertAll(anyList());

        mockMvc.perform(post("/skillProfiles/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void register_shouldInsert() throws Exception {
        SkillProfileDTO dto = new SkillProfileDTO();
        dto.setIdProfile(1);
        dto.setIdSkill(2);

        doNothing().when(skillProfileService).insert(any());

        mockMvc.perform(post("/skillProfiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldCallDelete() throws Exception {
        doNothing().when(skillProfileService).delete(1);

        mockMvc.perform(delete("/skillProfiles/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void listId_shouldReturnSkillProfileDTO() throws Exception {
        SkillProfile sp = new SkillProfile();
        sp.setIdSkillProfile(1);
        Profile profile = new Profile();
        profile.setIdProfile(2);
        Skill skill = new Skill();
        skill.setIdSkill(3);
        sp.setProfile(profile);
        sp.setSkill(skill);

        when(skillProfileService.listId(1)).thenReturn(sp);

        mockMvc.perform(get("/skillProfiles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSkillProfile").value(1))
                .andExpect(jsonPath("$.idProfile").value(2))
                .andExpect(jsonPath("$.idSkill").value(3));
    }

    @Test
    void list_shouldReturnListOfSkillProfiles() throws Exception {
        SkillProfile sp1 = new SkillProfile();
        sp1.setIdSkillProfile(1);
        sp1.setProfile(new Profile() {
            {
                setIdProfile(10);
            }
        });
        sp1.setSkill(new Skill() {
            {
                setIdSkill(20);
            }
        });
        SkillProfile sp2 = new SkillProfile();
        sp2.setIdSkillProfile(2);
        sp2.setProfile(new Profile() {
            {
                setIdProfile(30);
            }
        });
        sp2.setSkill(new Skill() {
            {
                setIdSkill(40);
            }
        });

        when(skillProfileService.list()).thenReturn(List.of(sp1, sp2));

        mockMvc.perform(get("/skillProfiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idSkillProfile").value(1))
                .andExpect(jsonPath("$[0].idProfile").value(10))
                .andExpect(jsonPath("$[0].idSkill").value(20))
                .andExpect(jsonPath("$[1].idSkillProfile").value(2))
                .andExpect(jsonPath("$[1].idProfile").value(30))
                .andExpect(jsonPath("$[1].idSkill").value(40));
    }

    @Test
    void update_shouldInsert() throws Exception {
        SkillProfileDTO dto = new SkillProfileDTO();
        dto.setIdSkillProfile(1);
        dto.setIdProfile(2);
        dto.setIdSkill(3);

        doNothing().when(skillProfileService).insert(any());

        mockMvc.perform(put("/skillProfiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateByProfile_shouldInsert() throws Exception {
        SkillProfileDTO dto = new SkillProfileDTO();
        dto.setIdSkillProfile(1);
        dto.setIdSkill(3);

        doNothing().when(skillProfileService).insert(any());

        mockMvc.perform(put("/skillProfiles/profile/{id}", 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void listByUser_shouldReturnListOfSkillProfiles() throws Exception {
        User user = new User();
        Profile profile = new Profile();
        profile.setIdProfile(10);
        user.setProfile(profile);

        SkillProfile sp = new SkillProfile();
        sp.setIdSkillProfile(100);
        sp.setProfile(profile);
        Skill skill = new Skill();
        skill.setIdSkill(200);
        sp.setSkill(skill);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(skillProfileService.findByProfile(profile)).thenReturn(List.of(sp));

        mockMvc.perform(get("/skillProfiles/user/{idUser}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].idSkillProfile").value(100))
                .andExpect(jsonPath("$[0].idProfile").value(10))
                .andExpect(jsonPath("$[0].idSkill").value(200));
    }

}
