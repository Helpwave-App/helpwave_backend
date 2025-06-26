package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;
import upc.helpwave.dtos.EmpairingDTO;
import upc.helpwave.dtos.NotificationMessageDTO;
import upc.helpwave.entities.Device;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Request;
import upc.helpwave.entities.User;
import upc.helpwave.entities.Videocall;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.FirebaseMessagingServiceImplement;
import upc.helpwave.serviceinterfaces.IEmpairingService;
import upc.helpwave.serviceinterfaces.IRequestService;
import upc.helpwave.serviceinterfaces.IVideocallService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmpairingController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)
})

@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "firebase.enabled=false")
class EmpairingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEmpairingService empairingService;

    @MockBean
    private IVideocallService videocallService;

    @MockBean
    private FirebaseMessagingServiceImplement firebaseMessagingService;

    @MockBean
    private IRequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    void register_shouldCreateEmpairing() throws Exception {
        EmpairingDTO dto = new EmpairingDTO();
        dto.setIdEmpairing(1);
        dto.setRequest(new Request());
        dto.setProfile(new Profile());
        dto.setStateEmpairing(true);

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/empairings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(empairingService, times(1)).insert(any(Empairing.class));
    }

    @Test
    void delete_shouldCallServiceDelete() throws Exception {
        int empairingId = 1;

        mockMvc.perform(delete("/empairings/{id}", empairingId))
                .andExpect(status().isOk());

        verify(empairingService, times(1)).delete(empairingId);
    }

    @Test
    void listId_shouldReturnEmpairingDTO() throws Exception {
        int empairingId = 1;
        Empairing empairing = new Empairing();
        empairing.setIdEmpairing(empairingId);

        when(empairingService.listId(empairingId)).thenReturn(empairing);

        EmpairingDTO expectedDTO = modelMapper.map(empairing, EmpairingDTO.class);

        mockMvc.perform(get("/empairings/{id}", empairingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEmpairing").value(expectedDTO.getIdEmpairing()));
    }

    @Test
    void list_shouldReturnListOfEmpairingDTOs() throws Exception {
        Empairing e1 = new Empairing();
        e1.setIdEmpairing(1);
        Empairing e2 = new Empairing();
        e2.setIdEmpairing(2);

        List<Empairing> empairings = Arrays.asList(e1, e2);
        when(empairingService.list()).thenReturn(empairings);

        mockMvc.perform(get("/empairings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(empairings.size()));
    }

    @Test
    void update_shouldUpdateEmpairing() throws Exception {
        EmpairingDTO dto = new EmpairingDTO();
        dto.setIdEmpairing(1);
        dto.setRequest(new Request());
        dto.setProfile(new Profile());
        dto.setStateEmpairing(true);

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/empairings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(empairingService, times(1)).insert(any(Empairing.class));
    }

    @Test
    void acceptEmpairing_shouldReturnNotFound_whenEmpairingIsNull() throws Exception {
        int empairingId = 1;
        when(empairingService.listId(empairingId)).thenReturn(null);

        mockMvc.perform(post("/empairings/accept/{empairingId}", empairingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void acceptEmpairing_shouldReturnForbidden_whenRequestIsNullOrInactive() throws Exception {
        int empairingId = 1;
        Empairing empairing = new Empairing();
        empairing.setRequest(null); // or inactive request
        when(empairingService.listId(empairingId)).thenReturn(empairing);

        mockMvc.perform(post("/empairings/accept/{empairingId}", empairingId))
                .andExpect(status().isForbidden());

        // caso 2: request inactivo
        Request request = new Request();
        request.setStateRequest(false);
        empairing.setRequest(request);
        when(empairingService.listId(empairingId)).thenReturn(empairing);

        mockMvc.perform(post("/empairings/accept/{empairingId}", empairingId))
                .andExpect(status().isForbidden());
    }

    @Test
    void acceptEmpairing_shouldReturnNotFound_whenVideocallIsNull() throws Exception {
        int empairingId = 1;
        Request request = new Request();
        request.setStateRequest(true);
        request.setProfile(new Profile());
        Empairing empairing = new Empairing();
        empairing.setRequest(request);

        when(empairingService.listId(empairingId)).thenReturn(empairing);
        when(empairingService.acceptEmpairing(empairingId)).thenReturn(null);

        mockMvc.perform(post("/empairings/accept/{empairingId}", empairingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void acceptEmpairing_shouldReturnOk_andSendNotification_whenTokenIsValid() throws Exception {
        int empairingId = 1;
        String expectedToken = "token123";
        String expectedChannel = "channelABC";

        // Setup Profile Accepter
        Profile accepter = new Profile();
        accepter.setName("Ana");
        accepter.setLastName("González");

        // Setup User (Solicitante)
        User user = new User();
        Device device = new Device();
        device.setTokenDevice("device_token_1");
        user.setDevices(List.of(device));

        // Setup Request
        Profile requesterProfile = new Profile();
        requesterProfile.setUser(user);
        Request request = new Request();
        request.setStateRequest(true);
        request.setProfile(requesterProfile);

        // Setup Empairing
        Empairing empairing = new Empairing();
        empairing.setRequest(request);
        empairing.setProfile(accepter);

        // Setup Videocall
        Videocall videocall = new Videocall();
        videocall.setToken(expectedToken);
        videocall.setChannel(expectedChannel);

        when(empairingService.listId(empairingId)).thenReturn(empairing);
        when(empairingService.acceptEmpairing(empairingId)).thenReturn(videocall);
        when(firebaseMessagingService.sendSilentNotificationByToken(any())).thenReturn("ok");

        mockMvc.perform(post("/empairings/accept/{empairingId}", empairingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken))
                .andExpect(jsonPath("$.channel").value(expectedChannel))
                .andExpect(jsonPath("$.name").value("Ana"))
                .andExpect(jsonPath("$.lastName").value("González"));

        verify(firebaseMessagingService, times(1)).sendSilentNotificationByToken(any(NotificationMessageDTO.class));
    }

    @Test
    void acceptEmpairing_shouldReturnOk_butNotSendNotification_whenTokenIsMissing() throws Exception {
        int empairingId = 1;

        Profile accepter = new Profile();
        accepter.setName("Carlos");
        accepter.setLastName("Vásquez");

        User user = new User();
        user.setDevices(List.of());

        Profile requesterProfile = new Profile();
        requesterProfile.setUser(user);
        Request request = new Request();
        request.setStateRequest(true);
        request.setProfile(requesterProfile);

        Empairing empairing = new Empairing();
        empairing.setRequest(request);
        empairing.setProfile(accepter);

        Videocall videocall = new Videocall();
        videocall.setToken("abc");
        videocall.setChannel("chan");

        when(empairingService.listId(empairingId)).thenReturn(empairing);
        when(empairingService.acceptEmpairing(empairingId)).thenReturn(videocall);

        mockMvc.perform(post("/empairings/accept/{empairingId}", empairingId))
                .andExpect(status().isOk());

        verify(firebaseMessagingService, never()).sendSilentNotificationByToken(any());
    }

}
