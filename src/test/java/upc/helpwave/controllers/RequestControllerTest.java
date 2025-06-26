package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.dtos.RequestDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.SkillRepository;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.FirebaseMessagingServiceImplement;
import upc.helpwave.serviceinterfaces.IEmpairingService;
import upc.helpwave.serviceinterfaces.IRequestService;
import upc.helpwave.serviceimplements.JwtUserDetailsService;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RequestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRequestService requestService;

    @MockBean
    private IEmpairingService empairingService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private SkillRepository skillRepository;

    @MockBean
    private FirebaseMessagingServiceImplement fMS;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void delete_shouldCallDeleteService() throws Exception {
        doNothing().when(requestService).delete(1);

        mockMvc.perform(delete("/requests/{id}", 1))
                .andExpect(status().isOk());

        verify(requestService, times(1)).delete(1);
    }

    @Test
    void listId_shouldReturnRequestDTO() throws Exception {
        Request req = new Request();
        req.setIdRequest(1);
        when(requestService.listId(1)).thenReturn(req);

        mockMvc.perform(get("/requests/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRequest").value(1));
    }

    @Test
    void list_shouldReturnListOfRequests() throws Exception {
        Request req1 = new Request();
        req1.setIdRequest(1);
        Request req2 = new Request();
        req2.setIdRequest(2);

        when(requestService.list()).thenReturn(List.of(req1, req2));

        mockMvc.perform(get("/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].idRequest").value(1))
                .andExpect(jsonPath("$[1].idRequest").value(2));
    }

    @Test
    void update_shouldCallInsertService() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setIdRequest(1);
        dto.setIdProfile(10);
        dto.setIdSkill(20);
        dto.setStateRequest(true);
        dto.setTokenDevice("token");

        doNothing().when(requestService).insert(any(Request.class));

        mockMvc.perform(put("/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(requestService, times(1)).insert(any(Request.class));
    }

    @Test
    void register_shouldReturnCreatedAndSendNotifications() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setIdProfile(10);
        dto.setIdSkill(20);
        dto.setTokenDevice("deviceToken");
        dto.setStateRequest(true);

        Profile profile = new Profile();
        profile.setIdProfile(10);
        profile.setName("John");
        profile.setLastName("Doe");

        Skill skill = new Skill();
        skill.setSkillDesc("SkillName");

        when(profileRepository.findById(10)).thenReturn(Optional.of(profile));
        when(skillRepository.findById(20)).thenReturn(Optional.of(skill));
        when(requestService.findRecentByProfile(eq(10), any(LocalDateTime.class))).thenReturn(Collections.emptyList());

        Request savedRequest = new Request();
        savedRequest.setIdRequest(100);
        savedRequest.setSkill(skill);
        savedRequest.setProfile(profile);
        when(empairingService.insert(any(Request.class))).thenReturn(savedRequest);

        Profile targetProfile = new Profile();
        targetProfile.setIdProfile(30);
        User targetUser = new User();
        Device device = new Device();
        device.setTokenDevice("token123");
        targetUser.setDevices(new ArrayList<>(List.of(device)));
        targetProfile.setUser(targetUser);

        Empairing empairing = new Empairing();
        empairing.setIdEmpairing(555);
        empairing.setProfile(targetProfile);

        when(empairingService.generateEmpairings(savedRequest)).thenReturn(List.of(empairing));

        when(fMS.sendNotificationByToken(any(NotificationMessageDTO.class))).thenReturn("mocked_response");

        mockMvc.perform(post("/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRequest").value(100))
                .andExpect(jsonPath("$.tokens", hasItem("token123")));

        ArgumentCaptor<NotificationMessageDTO> captor = ArgumentCaptor.forClass(NotificationMessageDTO.class);
        verify(fMS, times(1)).sendNotificationByToken(captor.capture());
        NotificationMessageDTO sentMessage = captor.getValue();
        assertEquals("Nueva solicitud de ayuda", sentMessage.getTitle());
        assertTrue(sentMessage.getBody().contains("John Doe"));
        assertEquals("token123", sentMessage.getTokenDevice());
        assertEquals("help_request", sentMessage.getData().get("type"));
        assertEquals("555", sentMessage.getData().get("idEmpairing"));
        assertEquals("SkillName", sentMessage.getData().get("skill"));
    }

    @Test
    void register_shouldReturnBadRequestWhenProfileOrSkillNotFound() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setIdProfile(999);
        dto.setIdSkill(888);

        when(profileRepository.findById(999)).thenReturn(Optional.empty());
        when(skillRepository.findById(888)).thenReturn(Optional.empty());

        mockMvc.perform(post("/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Perfil o habilidad no encontrados."));
    }

    @Test
    void register_shouldReturnTooManyRequestsWhenRecentRequestsExist() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setIdProfile(10);
        dto.setIdSkill(20);

        Profile profile = new Profile();
        profile.setIdProfile(10);

        Skill skill = new Skill();
        skill.setSkillDesc("SkillName");

        when(profileRepository.findById(10)).thenReturn(Optional.of(profile));
        when(skillRepository.findById(20)).thenReturn(Optional.of(skill));
        when(requestService.findRecentByProfile(eq(10), any(LocalDateTime.class))).thenReturn(List.of(new Request()));

        mockMvc.perform(post("/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isTooManyRequests())
                .andExpect(content().string("Solo puedes registrar una solicitud por minuto."));
    }

    @Test
    void cancelRequest_shouldReturnOkWhenRequestExists() throws Exception {
        Request request = new Request();
        request.setIdRequest(1);
        request.setStateRequest(true);

        when(requestService.listId(1)).thenReturn(request);
        doNothing().when(requestService).insert(any(Request.class));

        mockMvc.perform(put("/requests/cancel/{idRequest}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Solicitud cancelada correctamente."));

        assertFalse(request.getStateRequest());
        verify(requestService, times(1)).insert(request);
    }

    @Test
    void cancelRequest_shouldReturnNotFoundWhenRequestDoesNotExist() throws Exception {
        when(requestService.listId(1)).thenReturn(null);

        mockMvc.perform(put("/requests/cancel/{idRequest}", 1))
                .andExpect(status().isNotFound());
    }
}
