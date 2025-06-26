package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.AvailabilityDTO;
import upc.helpwave.dtos.AvailabilityListDTO;
import upc.helpwave.entities.Availability;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.IAvailabilityService;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AvailabilityController.class)
@TestPropertySource(properties = "firebase.enabled=false")
public class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAvailabilityService availabilityService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Should insert availabilities when profile exists")
    @Test
    public void registerBatch_shouldInsertAvailabilities_whenProfileExists() throws Exception {
        // Arrange
        Integer profileId = 1;
        AvailabilityDTO dto1 = new AvailabilityDTO();
        dto1.setDay("1");
        dto1.setHourStart(LocalTime.of(8, 0));
        dto1.setHourEnd(LocalTime.of(10, 0));
        dto1.setIdProfile(profileId);

        AvailabilityDTO dto2 = new AvailabilityDTO();
        dto2.setDay("2");
        dto2.setHourStart(LocalTime.of(10, 0));
        dto2.setHourEnd(LocalTime.of(12, 0));
        dto2.setIdProfile(profileId);

        AvailabilityListDTO requestDto = new AvailabilityListDTO();
        requestDto.setIdProfile(profileId);
        requestDto.setAvailabilities(List.of(dto1, dto2));

        Profile profile = new Profile();
        profile.setIdProfile(profileId);
        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

        // Act & Assert
        mockMvc.perform(post("/availabilities/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // Optional: verify
        verify(availabilityService, times(1)).insertAll(anyList());
    }

    @DisplayName("Should register single availability")
    @Test
    void register_shouldInsertSingleAvailability() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setDay("3");
        dto.setHourStart(LocalTime.of(9, 0));
        dto.setHourEnd(LocalTime.of(11, 0));

        mockMvc.perform(post("/availabilities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(availabilityService, times(1)).insert(any());
    }

    @DisplayName("Should delete availability by ID")
    @Test
    void delete_shouldDeleteAvailabilityById() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .delete("/availabilities/1"))
                .andExpect(status().isOk());

        verify(availabilityService, times(1)).delete(1);
    }

    @DisplayName("Should return availability by ID")
    @Test
    void listId_shouldReturnAvailabilityDTO() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setDay("4");
        dto.setHourStart(LocalTime.of(7, 0));
        dto.setHourEnd(LocalTime.of(8, 0));

        when(availabilityService.listId(1)).thenReturn(new Availability());

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .get("/availabilities/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("Should return all availabilities")
    @Test
    void list_shouldReturnAllAvailabilities() throws Exception {
        Availability availability = new Availability();
        availability.setDay("5");
        availability.setHourStart(LocalTime.of(6, 0));
        availability.setHourEnd(LocalTime.of(7, 0));

        when(availabilityService.list()).thenReturn(List.of(availability));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .get("/availabilities"))
                .andExpect(status().isOk());
    }

    @DisplayName("Should update availability")
    @Test
    void update_shouldUpdateAvailability() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setDay("6");
        dto.setHourStart(LocalTime.of(10, 0));
        dto.setHourEnd(LocalTime.of(11, 0));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .put("/availabilities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(availabilityService, times(1)).insert(any());
    }

    @DisplayName("Should update availability by profile ID")
    @Test
    void updateByProfile_shouldUpdateAvailabilityWithProfile() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setDay("7");
        dto.setHourStart(LocalTime.of(11, 0));
        dto.setHourEnd(LocalTime.of(12, 0));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .put("/availabilities/profile/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(availabilityService, times(1)).insert(any());
    }

    @DisplayName("Should return availabilities for user with profile")
    @Test
    void listByUser_shouldReturnAvailabilitiesForUserWithProfile() throws Exception {
        Integer userId = 1;
        Profile profile = new Profile();
        profile.setIdProfile(10);

        User user = new User();
        user.setIdUser(userId);
        user.setProfile(profile);

        Availability availability = new Availability();
        availability.setDay("1");
        availability.setHourStart(LocalTime.of(9, 0));
        availability.setHourEnd(LocalTime.of(10, 0));
        availability.setProfile(profile);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(availabilityService.findByProfile(profile)).thenReturn(List.of(availability));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .get("/availabilities/user/" + userId))
                .andExpect(status().isOk());
    }

}
