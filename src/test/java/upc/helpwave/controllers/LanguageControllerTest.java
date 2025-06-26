package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.LanguageDTO;
import upc.helpwave.entities.Language;
import upc.helpwave.security.JwtTokenUtil;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.ILanguageService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LanguageController.class)
@AutoConfigureMockMvc(addFilters = false)
class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ILanguageService languageService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    void register_shouldCreateLanguage() throws Exception {
        LanguageDTO dto = new LanguageDTO();
        dto.setIdLanguage(1);
        dto.setNameLanguage("English");

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/languages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(languageService, times(1)).insert(any(Language.class));
    }

    @Test
    void delete_shouldRemoveLanguage() throws Exception {
        int id = 1;

        mockMvc.perform(delete("/languages/{id}", id))
                .andExpect(status().isOk());

        verify(languageService, times(1)).delete(id);
    }

    @Test
    void listId_shouldReturnLanguageDTO() throws Exception {
        int id = 1;
        Language language = new Language();
        language.setIdLanguage(id);
        language.setNameLanguage("Spanish");

        when(languageService.listId(id)).thenReturn(language);

        mockMvc.perform(get("/languages/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLanguage").value(id))
                .andExpect(jsonPath("$.nameLanguage").value("Spanish"));
    }

    @Test
    void list_shouldReturnListOfLanguages() throws Exception {
        Language l1 = new Language();
        l1.setIdLanguage(1);
        l1.setNameLanguage("English");

        Language l2 = new Language();
        l2.setIdLanguage(2);
        l2.setNameLanguage("French");

        List<Language> languages = Arrays.asList(l1, l2);

        when(languageService.list()).thenReturn(languages);

        mockMvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(languages.size()))
                .andExpect(jsonPath("$[0].idLanguage").value(1))
                .andExpect(jsonPath("$[0].nameLanguage").value("English"))
                .andExpect(jsonPath("$[1].idLanguage").value(2))
                .andExpect(jsonPath("$[1].nameLanguage").value("French"));
    }

    @Test
    void update_shouldModifyLanguage() throws Exception {
        LanguageDTO dto = new LanguageDTO();
        dto.setIdLanguage(1);
        dto.setNameLanguage("Updated");

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/languages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(languageService, times(1)).insert(any(Language.class));
    }
}
