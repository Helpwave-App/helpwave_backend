package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.DeviceDTO;
import upc.helpwave.dtos.DeviceUpsertDTO;
import upc.helpwave.entities.Device;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.IDeviceService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "firebase.enabled=false")
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDeviceService deviceService;

    @MockBean
    private FirebaseMessaging firebaseMessaging;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getDeviceById_shouldReturnDeviceDTO() throws Exception {
        Device device = new Device();
        device.setIdDevice(1);
        device.setTokenDevice("abc123");
        device.setRegistrationDate(LocalDateTime.now());
        device.setUser(null); // Asume que el mapping a DTO se maneja en el controller

        when(deviceService.listId(1)).thenReturn(device);

        mockMvc.perform(get("/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDevice").value(1))
                .andExpect(jsonPath("$.tokenDevice").value("abc123"));
    }

    @Test
    void getAllDevices_shouldReturnListOfDeviceDTOs() throws Exception {
        Device d1 = new Device();
        d1.setIdDevice(1);
        d1.setTokenDevice("token1");
        d1.setRegistrationDate(LocalDateTime.now());

        Device d2 = new Device();
        d2.setIdDevice(2);
        d2.setTokenDevice("token2");
        d2.setRegistrationDate(LocalDateTime.now());

        List<Device> devices = Arrays.asList(d1, d2);

        when(deviceService.list()).thenReturn(devices);

        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void registerDevice_shouldInsertDevice() throws Exception {
        DeviceDTO dto = new DeviceDTO();
        dto.setTokenDevice("xyz789");
        dto.setIdUser(5);

        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(deviceService).insert(any(Device.class));
    }

    @Test
    void updateDevice_shouldUpdateDevice() throws Exception {
        DeviceDTO dto = new DeviceDTO();
        dto.setIdDevice(1);
        dto.setTokenDevice("updatedToken");
        dto.setIdUser(3);

        mockMvc.perform(put("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(deviceService).insert(any(Device.class));
    }

    @Test
    void deleteDevice_shouldCallServiceDeleteMethod() throws Exception {
        mockMvc.perform(delete("/devices/abc123"))
                .andExpect(status().isOk());

        verify(deviceService).delete("abc123");
    }

    @Test
    void upsertDevice_shouldReturnSuccessMessage() throws Exception {
        DeviceUpsertDTO dto = new DeviceUpsertDTO();
        dto.setIdUser(1);
        dto.setOldTokenDevice("old-token");
        dto.setNewTokenDevice("new-token");

        mockMvc.perform(post("/devices/upsert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Device procesado correctamente"));

        verify(deviceService).upsert(refEq(dto));
    }

    @Test
    void upsertDevice_shouldReturnErrorMessage_whenExceptionThrown() throws Exception {
        DeviceUpsertDTO dto = new DeviceUpsertDTO();
        dto.setIdUser(1);
        dto.setOldTokenDevice("old-token");
        dto.setNewTokenDevice("new-token");

        doThrow(new RuntimeException("error")).when(deviceService).upsert(any(DeviceUpsertDTO.class));

        mockMvc.perform(post("/devices/upsert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("error"));
    }

}
