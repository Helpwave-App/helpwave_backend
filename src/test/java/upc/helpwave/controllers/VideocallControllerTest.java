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
import upc.helpwave.dtos.VideocallDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.FirebaseMessagingServiceImplement;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.IVideocallService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VideocallController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VideocallControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVideocallService videocallService;

    @MockBean
    private FirebaseMessagingServiceImplement firebaseMessagingService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findVideocallByEmpairingId_shouldReturnVideocallDTO_whenFound() throws Exception {
        Videocall videocall = new Videocall();
        videocall.setToken("token123");
        videocall.setChannel("channelABC");
        videocall.setEmpairing(new Empairing());

        when(videocallService.findByEmpairingId(1)).thenReturn(Optional.of(videocall));

        mockMvc.perform(get("/videocalls/empairings/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.channel").value("channelABC"));
    }

    @Test
    void findVideocallByEmpairingId_shouldReturn404_whenNotFound() throws Exception {
        when(videocallService.findByEmpairingId(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/videocalls/empairings/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void register_shouldCallInsert() throws Exception {
        VideocallDTO dto = new VideocallDTO();
        dto.setToken("tokenX");
        dto.setChannel("channelX");
        dto.setName("John");
        dto.setLastName("Doe");

        mockMvc.perform(post("/videocalls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(videocallService).insert(any(Videocall.class));
    }

    @Test
    void delete_shouldCallDelete() throws Exception {
        mockMvc.perform(delete("/videocalls/{id}", 10))
                .andExpect(status().isOk());

        verify(videocallService).delete(10);
    }

    @Test
    void listId_shouldReturnVideocallDTO() throws Exception {
        Videocall videocall = new Videocall();
        videocall.setToken("tokenY");
        videocall.setChannel("channelY");

        when(videocallService.listId(5)).thenReturn(videocall);

        mockMvc.perform(get("/videocalls/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("tokenY"))
                .andExpect(jsonPath("$.channel").value("channelY"));
    }

    @Test
    void list_shouldReturnListOfVideocallDTO() throws Exception {
        Videocall v1 = new Videocall();
        v1.setToken("t1");
        v1.setChannel("c1");
        Videocall v2 = new Videocall();
        v2.setToken("t2");
        v2.setChannel("c2");

        when(videocallService.list()).thenReturn(List.of(v1, v2));

        mockMvc.perform(get("/videocalls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].token").value("t1"))
                .andExpect(jsonPath("$[1].channel").value("c2"));
    }

    @Test
    void update_shouldCallInsert() throws Exception {
        VideocallDTO dto = new VideocallDTO();
        dto.setToken("updatedToken");
        dto.setChannel("updatedChannel");

        mockMvc.perform(put("/videocalls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(videocallService).insert(any(Videocall.class));
    }

    @Test
    void endVideocall_shouldReturnBadRequest_whenChannelMissing() throws Exception {
        mockMvc.perform(post("/videocalls/end")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Channel is required."));
    }

    @Test
    void endVideocall_shouldReturnNotFound_whenVideocallNotFound() throws Exception {
        Map<String, String> payload = Map.of("channel", "nonexistent");

        when(videocallService.findByChannel("nonexistent")).thenReturn(Optional.empty());

        mockMvc.perform(post("/videocalls/end")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNotFound());
    }

    @Test
    void endVideocall_shouldUpdateEndTimeAndSendNotifications() throws Exception {
        Device device1 = new Device();
        device1.setTokenDevice("tokenDevice1");
        Device device2 = new Device();
        device2.setTokenDevice("tokenDevice2");

        User requesterUser = new User();
        requesterUser.setDevices(List.of(device1));

        Profile requesterProfile = new Profile();
        requesterProfile.setUser(requesterUser);

        Request request = new Request();
        request.setProfile(requesterProfile);

        User volunteerUser = new User();
        volunteerUser.setDevices(List.of(device2));

        Profile volunteerProfile = new Profile();
        volunteerProfile.setUser(volunteerUser);

        Empairing empairing = new Empairing();
        empairing.setRequest(request);
        empairing.setProfile(volunteerProfile);

        Videocall videocall = new Videocall();
        videocall.setIdVideocall(99);
        videocall.setChannel("channelEnd");
        videocall.setEmpairing(empairing);

        when(videocallService.findByChannel("channelEnd")).thenReturn(Optional.of(videocall));
        doNothing().when(videocallService).insert(any(Videocall.class));
        when(firebaseMessagingService.sendSilentNotificationByToken(any(NotificationMessageDTO.class)))
                .thenReturn("mocked-message-id");

        Map<String, String> payload = Map.of("channel", "channelEnd");

        mockMvc.perform(post("/videocalls/end")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().string("Videollamada finalizada."));

        ArgumentCaptor<Videocall> captor = ArgumentCaptor.forClass(Videocall.class);
        verify(videocallService).insert(captor.capture());
        Videocall saved = captor.getValue();
        assert saved.getEndVideocall() != null;

        verify(firebaseMessagingService, times(2)).sendSilentNotificationByToken(any(NotificationMessageDTO.class));
    }
}
