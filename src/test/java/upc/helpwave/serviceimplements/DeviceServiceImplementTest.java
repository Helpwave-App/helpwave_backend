package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import upc.helpwave.dtos.DeviceUpsertDTO;
import upc.helpwave.entities.Device;
import upc.helpwave.repositories.DeviceRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceImplementTest {

    @InjectMocks
    private DeviceServiceImplement service;

    @Mock
    private DeviceRepository deviceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void upsert_shouldThrowExceptionWhenParamsAreInvalid() {
        DeviceUpsertDTO dto = new DeviceUpsertDTO();
        assertThrows(RuntimeException.class, () -> service.upsert(dto));
    }

    @Test
    void upsert_shouldUpdateDeviceWhenOldTokenExists() {
        DeviceUpsertDTO dto = new DeviceUpsertDTO();
        dto.setOldTokenDevice("oldToken");
        dto.setNewTokenDevice("newToken");
        dto.setIdUser(1);

        Device existingDevice = new Device();
        when(deviceRepository.findByTokenDevice("oldToken")).thenReturn(Optional.of(existingDevice));

        service.upsert(dto);

        verify(deviceRepository).save(argThat(device -> device.getTokenDevice().equals("newToken") &&
                device.getUser().getIdUser().equals(1)));
    }

    @Test
    void upsert_shouldReassociateUserIfNewTokenExists() {
        DeviceUpsertDTO dto = new DeviceUpsertDTO();
        dto.setNewTokenDevice("existingToken");
        dto.setIdUser(2);

        Device existingDevice = new Device();
        when(deviceRepository.findByTokenDevice("existingToken")).thenReturn(Optional.of(existingDevice));

        service.upsert(dto);

        verify(deviceRepository).save(argThat(device -> device.getUser().getIdUser().equals(2)));
    }

    @Test
    void upsert_shouldCreateNewDeviceIfTokenDoesNotExist() {
        DeviceUpsertDTO dto = new DeviceUpsertDTO();
        dto.setNewTokenDevice("newToken");
        dto.setIdUser(3);

        when(deviceRepository.findByTokenDevice("newToken")).thenReturn(Optional.empty());

        service.upsert(dto);

        verify(deviceRepository).save(argThat(device -> device.getTokenDevice().equals("newToken") &&
                device.getUser().getIdUser().equals(3)));
    }

    @Test
    void delete_shouldRemoveDeviceWhenTokenExists() {
        Device device = new Device();
        when(deviceRepository.findByTokenDevice("token")).thenReturn(Optional.of(device));

        service.delete("token");

        verify(deviceRepository).delete(device);
    }

    @Test
    void delete_shouldDoNothingWhenTokenDoesNotExist() {
        when(deviceRepository.findByTokenDevice("token")).thenReturn(Optional.empty());

        service.delete("token");

        verify(deviceRepository, never()).delete(any());
    }
}
