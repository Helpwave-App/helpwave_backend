package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.LanguageProfileDTO;
import upc.helpwave.dtos.LanguageProfileListDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.LanguageRepository;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.security.JwtTokenUtil;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.ILanguageProfileService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LanguageProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LanguageProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ILanguageProfileService languageProfileService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private LanguageRepository languageRepository;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldSaveLanguageProfile() throws Exception {
        LanguageProfileDTO dto = new LanguageProfileDTO();
        dto.setIdLanguage(1);
        dto.setIdProfile(1);
        dto.setIdLanguageProfile(10);

        mockMvc.perform(post("/languageProfiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(languageProfileService, times(1)).insert(any(LanguageProfile.class));
    }

    @Test
    void delete_shouldRemoveLanguageProfile() throws Exception {
        mockMvc.perform(delete("/languageProfiles/1"))
                .andExpect(status().isOk());

        verify(languageProfileService).delete(1);
    }

    @Test
    void listId_shouldReturnDto() throws Exception {
        LanguageProfile languageProfile = new LanguageProfile();
        languageProfile.setIdLanguageProfile(1);
        languageProfile.setProfile(new Profile(2));
        languageProfile.setLanguage(new Language(3));

        when(languageProfileService.listId(1)).thenReturn(languageProfile);

        mockMvc.perform(get("/languageProfiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLanguageProfile").value(1))
                .andExpect(jsonPath("$.idLanguage").value(3))
                .andExpect(jsonPath("$.idProfile").value(2));
    }

    @Test
    void list_shouldReturnAllLanguageProfiles() throws Exception {
        LanguageProfile lp1 = new LanguageProfile(1, new Language(3), new Profile(2));
        LanguageProfile lp2 = new LanguageProfile(2, new Language(5), new Profile(4));
        when(languageProfileService.list()).thenReturn(List.of(lp1, lp2));

        mockMvc.perform(get("/languageProfiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void update_shouldModifyLanguageProfile() throws Exception {
        LanguageProfileDTO dto = new LanguageProfileDTO();
        dto.setIdLanguageProfile(1);
        dto.setIdLanguage(2);
        dto.setIdProfile(3);

        mockMvc.perform(put("/languageProfiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(languageProfileService).insert(any(LanguageProfile.class));
    }

    @Test
    void registerBatch_shouldSaveMultipleLanguageProfiles() throws Exception {
        Profile profile = new Profile(1);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(languageRepository.findById(anyInt())).thenReturn(Optional.of(new Language()));

        LanguageProfileListDTO dto = new LanguageProfileListDTO();
        dto.setIdProfile(1);
        dto.setLanguageIds(Arrays.asList(1, 2, 3));

        mockMvc.perform(post("/languageProfiles/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(languageProfileService).insertAll(anyList());
    }

    @Test
    void listByUser_shouldReturnLanguageProfilesForUser() throws Exception {
        Profile profile = new Profile(2);
        User user = new User();
        user.setProfile(profile);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Language language = new Language();
        language.setIdLanguage(10); // <-- el id esperado por el test

        LanguageProfile lp = new LanguageProfile();
        lp.setIdLanguageProfile(5); // <-- el id esperado por el test
        lp.setLanguage(language);
        lp.setProfile(profile);

        when(languageProfileService.findByProfile(profile)).thenReturn(List.of(lp));

        mockMvc.perform(get("/languageProfiles/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idLanguageProfile").value(5))
                .andExpect(jsonPath("$[0].idLanguage").value(10))
                .andExpect(jsonPath("$[0].idProfile").value(2));
    }

    @Test
    void updateByProfile_shouldUpdateProfileLanguage() throws Exception {
        LanguageProfileDTO dto = new LanguageProfileDTO();
        dto.setIdLanguageProfile(1);
        dto.setIdLanguage(2);

        mockMvc.perform(put("/languageProfiles/profile/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(languageProfileService).insert(any(LanguageProfile.class));
    }
}
