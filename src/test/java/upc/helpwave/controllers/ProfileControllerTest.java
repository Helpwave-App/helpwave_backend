package upc.helpwave.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import upc.helpwave.dtos.ProfileDTO;
import upc.helpwave.entities.Level;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.LevelRepository;
import upc.helpwave.serviceinterfaces.IProfileService;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProfileService profileService;

    @MockBean
    private LevelRepository levelRepository;

    @MockBean
    private upc.helpwave.security.JwtRequestFilter jwtRequestFilter;

    @MockBean
    private upc.helpwave.serviceimplements.JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private upc.helpwave.security.JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldReturnOk() throws Exception {
        ProfileDTO dto = new ProfileDTO();
        dto.setName("John");
        dto.setLastName("Doe");

        mockMvc.perform(post("/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(profileService).insert(any(Profile.class));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        int profileId = 1;

        doNothing().when(profileService).delete(profileId);

        mockMvc.perform(delete("/profiles/{id}", profileId))
                .andExpect(status().isOk());

        verify(profileService).delete(profileId);
    }

    @Test
    void listId_shouldReturnProfile_whenExists() throws Exception {
        int profileId = 1;

        Profile profile = new Profile();
        profile.setIdProfile(profileId);
        profile.setName("Alice");
        profile.setLastName("Smith");

        when(profileService.listId(profileId)).thenReturn(profile);

        mockMvc.perform(get("/profiles/{id}", profileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProfile").value(profileId))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void listId_shouldReturnNotFound_whenDoesNotExist() throws Exception {
        Integer id = 1;
        when(profileService.listId(id)).thenReturn(null);

        mockMvc.perform(get("/profiles/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void list_shouldReturnProfiles() throws Exception {
        Profile p1 = new Profile();
        p1.setIdProfile(1);
        p1.setName("User1");

        Profile p2 = new Profile();
        p2.setIdProfile(2);
        p2.setName("User2");

        when(profileService.list()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProfile").value(1))
                .andExpect(jsonPath("$[0].name").value("User1"))
                .andExpect(jsonPath("$[1].idProfile").value(2))
                .andExpect(jsonPath("$[1].name").value("User2"));
    }

    @Test
    void updatePartially_shouldReturnOk_whenProfileAndLevelExist() throws Exception {
        Integer profileId = 1;

        Profile existingProfile = new Profile();
        existingProfile.setIdProfile(profileId);

        Level level = new Level();
        level.setIdLevel(2);

        ProfileDTO updateDto = new ProfileDTO();
        updateDto.setIdLevel(2);
        updateDto.setName("UpdatedName");

        when(profileService.findById(profileId)).thenReturn(Optional.of(existingProfile));
        when(levelRepository.findById(2)).thenReturn(Optional.of(level));
        doNothing().when(profileService).insert(any(Profile.class));

        mockMvc.perform(patch("/profiles/{id}", profileId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Perfil actualizado parcialmente"));

        verify(profileService).insert(
                argThat(profile -> profile.getLevel().equals(level) && "UpdatedName".equals(profile.getName())));
    }

    @Test
    void updatePartially_shouldReturnNotFound_whenProfileDoesNotExist() throws Exception {
        Integer profileId = 1;
        ProfileDTO updateDto = new ProfileDTO();
        updateDto.setName("UpdatedName");

        when(profileService.findById(profileId)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/profiles/{id}", profileId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePartially_shouldReturnBadRequest_whenLevelDoesNotExist() throws Exception {
        Integer profileId = 1;

        Profile existingProfile = new Profile();
        existingProfile.setIdProfile(profileId);

        ProfileDTO updateDto = new ProfileDTO();
        updateDto.setIdLevel(99);

        when(profileService.findById(profileId)).thenReturn(Optional.of(existingProfile));
        when(levelRepository.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/profiles/{id}", profileId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Nivel no encontrado."));
    }
}
