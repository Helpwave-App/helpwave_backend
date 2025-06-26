package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.LevelDTO;
import upc.helpwave.entities.Level;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.LevelRepository;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.security.JwtTokenUtil;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.ILevelService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LevelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LevelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ILevelService levelService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private LevelRepository levelRepository;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getLevelInfo_shouldReturnProgress() throws Exception {
        Profile profile = new Profile();
        profile.setAssistances(5);
        profile.setScoreProfile(new BigDecimal("4.5"));
        profile.setLevel(new Level(1, "Intermedio", 5, 10, "url1"));

        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(levelRepository.findFirstByMinRequestGreaterThanOrderByMinRequestAsc(5))
                .thenReturn(Optional.of(new Level(2, "Avanzado", 10, 20, "url2")));

        mockMvc.perform(get("/levels/progress/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assistances").value(5))
                .andExpect(jsonPath("$.scoreProfile").value(4.5))
                .andExpect(jsonPath("$.currentLevel").value("Intermedio"))
                .andExpect(jsonPath("$.nextLevel").value("Avanzado"));
    }

    @Test
    void getLevelInfo_shouldReturn404IfProfileNotFound() throws Exception {
        when(profileRepository.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/levels/progress/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void register_shouldCreateLevel() throws Exception {
        LevelDTO dto = new LevelDTO();
        dto.setIdLevel(1);
        dto.setNameLevel("Básico");
        dto.setMinRequest(0);
        dto.setMaxRequest(5);
        dto.setPhotoUrl("url");

        mockMvc.perform(post("/levels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(levelService, times(1)).insert(any(Level.class));
    }

    @Test
    void delete_shouldRemoveLevel() throws Exception {
        mockMvc.perform(delete("/levels/1"))
                .andExpect(status().isOk());

        verify(levelService, times(1)).delete(1);
    }

    @Test
    void listId_shouldReturnLevelDTO() throws Exception {
        Level level = new Level(1, "Básico", 0, 10, "url");
        when(levelService.listId(1)).thenReturn(level);

        mockMvc.perform(get("/levels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLevel").value(1))
                .andExpect(jsonPath("$.nameLevel").value("Básico"));
    }

    @Test
    void list_shouldReturnAllLevels() throws Exception {
        List<Level> levels = List.of(
                new Level(1, "Básico", 0, 10, "url1"),
                new Level(2, "Avanzado", 10, 20, "url2"));
        when(levelService.list()).thenReturn(levels);

        mockMvc.perform(get("/levels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nameLevel").value("Básico"));
    }

    @Test
    void update_shouldModifyLevel() throws Exception {
        LevelDTO dto = new LevelDTO();
        dto.setIdLevel(1);
        dto.setNameLevel("Intermedio");
        dto.setMinRequest(5);
        dto.setMaxRequest(15);
        dto.setPhotoUrl("url3");

        mockMvc.perform(put("/levels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(levelService, times(1)).insert(any(Level.class));
    }
}
