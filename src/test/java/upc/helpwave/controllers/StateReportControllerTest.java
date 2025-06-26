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
import upc.helpwave.dtos.StateReportDTO;
import upc.helpwave.entities.StateReport;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.IStateReportService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StateReportController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StateReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStateReportService stateReportService;

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
        when(modelMapper.map(any(StateReportDTO.class), eq(StateReport.class))).thenAnswer(invocation -> {
            StateReportDTO dto = invocation.getArgument(0);
            StateReport entity = new StateReport();
            entity.setIdStateReport(dto.getIdStateReport());
            entity.setDescriptionState(dto.getDescriptionState());
            return entity;
        });

        when(modelMapper.map(any(StateReport.class), eq(StateReportDTO.class))).thenAnswer(invocation -> {
            StateReport entity = invocation.getArgument(0);
            StateReportDTO dto = new StateReportDTO();
            dto.setIdStateReport(entity.getIdStateReport());
            dto.setDescriptionState(entity.getDescriptionState());
            return dto;
        });
    }

    @Test
    void register_shouldCallInsert() throws Exception {
        StateReportDTO dto = new StateReportDTO();
        dto.setIdStateReport(1);
        dto.setDescriptionState("Pending");

        doNothing().when(stateReportService).insert(any(StateReport.class));

        mockMvc.perform(post("/stateReports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldCallDelete() throws Exception {
        doNothing().when(stateReportService).delete(1);

        mockMvc.perform(delete("/stateReports/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void listId_shouldReturnStateReportDTO() throws Exception {
        StateReport entity = new StateReport();
        entity.setIdStateReport(1);
        entity.setDescriptionState("Completed");

        when(stateReportService.listId(1)).thenReturn(entity);

        mockMvc.perform(get("/stateReports/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStateReport").value(1))
                .andExpect(jsonPath("$.descriptionState").value("Completed"));
    }

    @Test
    void list_shouldReturnListOfStateReportDTO() throws Exception {
        StateReport entity1 = new StateReport();
        entity1.setIdStateReport(1);
        entity1.setDescriptionState("Pending");

        StateReport entity2 = new StateReport();
        entity2.setIdStateReport(2);
        entity2.setDescriptionState("Completed");

        when(stateReportService.list()).thenReturn(List.of(entity1, entity2));

        mockMvc.perform(get("/stateReports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idStateReport").value(1))
                .andExpect(jsonPath("$[0].descriptionState").value("Pending"))
                .andExpect(jsonPath("$[1].idStateReport").value(2))
                .andExpect(jsonPath("$[1].descriptionState").value("Completed"));
    }

    @Test
    void update_shouldCallInsert() throws Exception {
        StateReportDTO dto = new StateReportDTO();
        dto.setIdStateReport(1);
        dto.setDescriptionState("Updated");

        doNothing().when(stateReportService).insert(any(StateReport.class));

        mockMvc.perform(put("/stateReports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
