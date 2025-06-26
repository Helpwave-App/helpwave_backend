package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.TypeReportDTO;
import upc.helpwave.entities.TypeReport;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.ITypeReportService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeReportController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TypeReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITypeReportService typeReportService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldCallInsert() throws Exception {
        TypeReportDTO dto = new TypeReportDTO();
        dto.setIdTypeReport(1);
        dto.setTypeDesc("Spam");

        doNothing().when(typeReportService).insert(any(TypeReport.class));

        mockMvc.perform(post("/typeReports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldCallDelete() throws Exception {
        doNothing().when(typeReportService).delete(1);

        mockMvc.perform(delete("/typeReports/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void listId_shouldReturnTypeReportDTO() throws Exception {
        TypeReport entity = new TypeReport();
        entity.setIdTypeReport(1);
        entity.setTypeDesc("Fraud");

        when(typeReportService.listId(1)).thenReturn(entity);

        mockMvc.perform(get("/typeReports/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTypeReport").value(1))
                .andExpect(jsonPath("$.typeDesc").value("Fraud"));
    }

    @Test
    void list_shouldReturnListOfTypeReportDTO() throws Exception {
        TypeReport entity1 = new TypeReport();
        entity1.setIdTypeReport(1);
        entity1.setTypeDesc("Spam");
        TypeReport entity2 = new TypeReport();
        entity2.setIdTypeReport(2);
        entity2.setTypeDesc("Abuse");

        when(typeReportService.list()).thenReturn(List.of(entity1, entity2));

        mockMvc.perform(get("/typeReports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idTypeReport").value(1))
                .andExpect(jsonPath("$[0].typeDesc").value("Spam"))
                .andExpect(jsonPath("$[1].idTypeReport").value(2))
                .andExpect(jsonPath("$[1].typeDesc").value("Abuse"));
    }

    @Test
    void update_shouldCallInsert() throws Exception {
        TypeReportDTO dto = new TypeReportDTO();
        dto.setIdTypeReport(3);
        dto.setTypeDesc("Harassment");

        doNothing().when(typeReportService).insert(any(TypeReport.class));

        mockMvc.perform(put("/typeReports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
