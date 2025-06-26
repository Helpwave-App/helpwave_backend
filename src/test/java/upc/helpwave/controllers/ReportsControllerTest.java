package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.ReportsDTO;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Request;
import upc.helpwave.entities.Reports;
import upc.helpwave.entities.StateReport;
import upc.helpwave.entities.TypeReport;
import upc.helpwave.entities.Videocall;
import upc.helpwave.repositories.StateReportRepository;
import upc.helpwave.repositories.TypeReportRepository;
import upc.helpwave.repositories.VideocallRepository;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.IReportsService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReportsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReportsService reportsService;

    @MockBean
    private VideocallRepository videocallRepository;

    @MockBean
    private TypeReportRepository typeReportRepository;

    @MockBean
    private StateReportRepository stateReportRepository;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldReturnCreated_whenValidInput() throws Exception {
        ReportsDTO dto = new ReportsDTO();
        dto.setIdVideocall(1);
        dto.setIdTypeReport(2);
        dto.setDescriptionReport("Reporte válido");
        dto.setIdProfile(10);

        Profile requesterProfile = new Profile();
        requesterProfile.setIdProfile(10);
        Profile volunteerProfile = new Profile();
        volunteerProfile.setIdProfile(20);

        Request request = new Request();
        request.setProfile(requesterProfile);

        Empairing empairing = new Empairing();
        empairing.setRequest(request);
        empairing.setProfile(volunteerProfile);

        Videocall videocall = new Videocall();
        videocall.setEmpairing(empairing);

        when(videocallRepository.findById(1)).thenReturn(Optional.of(videocall));
        when(typeReportRepository.findById(2)).thenReturn(Optional.of(new TypeReport()));
        when(stateReportRepository.findById(1)).thenReturn(Optional.of(new StateReport()));

        doNothing().when(reportsService).insert(any(Reports.class));

        mockMvc.perform(post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Reporte registrado correctamente."));
    }

    @Test
    void register_shouldReturnBadRequest_whenVideocallNotFound() throws Exception {
        ReportsDTO dto = new ReportsDTO();
        dto.setIdVideocall(999);
        dto.setIdTypeReport(2);
        dto.setDescriptionReport("Reporte inválido");
        dto.setIdProfile(10);

        when(videocallRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Videocall, TypeReport o StateReport no encontrados."));
    }

    @Test
    void listId_shouldReturnReportDTO() throws Exception {
        Reports report = new Reports();
        report.setIdReport(1); // no lo usaremos en el test
        Videocall videocall = new Videocall();
        videocall.setIdVideocall(100);
        report.setVideocall(videocall);
        report.setDescriptionReport("Descripción ejemplo");

        when(reportsService.listId(1)).thenReturn(report);

        mockMvc.perform(get("/reports/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVideocall").value(100))
                .andExpect(jsonPath("$.descriptionReport").value("Descripción ejemplo"));
    }

    @Test
    void list_shouldReturnListOfReports() throws Exception {
        Reports report1 = new Reports();
        Videocall videocall1 = new Videocall();
        videocall1.setIdVideocall(101);
        report1.setVideocall(videocall1);
        report1.setDescriptionReport("Desc 1");

        Reports report2 = new Reports();
        Videocall videocall2 = new Videocall();
        videocall2.setIdVideocall(102);
        report2.setVideocall(videocall2);
        report2.setDescriptionReport("Desc 2");

        when(reportsService.list()).thenReturn(List.of(report1, report2));

        mockMvc.perform(get("/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idVideocall").value(101))
                .andExpect(jsonPath("$[0].descriptionReport").value("Desc 1"))
                .andExpect(jsonPath("$[1].idVideocall").value(102))
                .andExpect(jsonPath("$[1].descriptionReport").value("Desc 2"));
    }

    @Test
    void update_shouldCallInsertService() throws Exception {
        ReportsDTO dto = new ReportsDTO();
        dto.setIdVideocall(1);
        dto.setIdTypeReport(2);
        dto.setDescriptionReport("Actualizar reporte");
        dto.setIdProfile(10);

        doNothing().when(reportsService).insert(any(Reports.class));

        mockMvc.perform(put("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldCallDeleteService() throws Exception {
        doNothing().when(reportsService).delete(1);

        mockMvc.perform(delete("/reports/{id}", 1))
                .andExpect(status().isOk());
    }
}
