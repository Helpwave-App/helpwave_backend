package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import upc.helpwave.entities.Empairing;
import upc.helpwave.entities.Videocall;
import upc.helpwave.repositories.VideocallRepository;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VideocallServiceImplementTest {

    @InjectMocks
    private VideocallServiceImplement service;

    @Mock
    private VideocallRepository vR;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Asignar valores para las variables privadas usando reflexión
        Field appIdField = VideocallServiceImplement.class.getDeclaredField("appId");
        appIdField.setAccessible(true);
        appIdField.set(service, "testAppId");

        Field certField = VideocallServiceImplement.class.getDeclaredField("appCertificate");
        certField.setAccessible(true);
        certField.set(service, "testAppCertificate");
    }

    @Test
    void createVideocall_shouldPersistAndReturnVideocall() {
        Empairing emp = new Empairing();
        emp.setIdEmpairing(42);

        when(vR.save(any(Videocall.class))).thenAnswer(i -> i.getArguments()[0]);

        Videocall result = service.createVideocall(emp);

        assertNotNull(result);
        assertEquals(emp, result.getEmpairing());
        assertNotNull(result.getChannel());
        assertNotNull(result.getToken());
        verify(vR, times(1)).save(any(Videocall.class));
    }

    @Test
    void findByEmpairingId_shouldReturnMatchingVideocall() {
        Empairing emp = new Empairing();
        emp.setIdEmpairing(1);
        Videocall vc = new Videocall();
        vc.setEmpairing(emp);

        when(vR.findByEmpairing(any(Empairing.class))).thenReturn(Optional.of(vc));

        Optional<Videocall> result = service.findByEmpairingId(1);

        assertTrue(result.isPresent());
        assertEquals(vc, result.get());
    }

    @Test
    void findByChannel_shouldReturnExpectedResult() {
        Videocall vc = new Videocall();
        when(vR.findByChannel("test")).thenReturn(Optional.of(vc));

        Optional<Videocall> result = service.findByChannel("test");

        assertTrue(result.isPresent());
        assertEquals(vc, result.get());
    }
}
