package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpairingServiceImplementTest {

    @InjectMocks
    private EmpairingServiceImplement service;

    @Mock
    private EmpairingRepository empairingRepo;
    @Mock
    private AvailabilityRepository availabilityRepo;
    @Mock
    private VideocallServiceImplement videocallService;
    @Mock
    private RequestRepository requestRepo;
    @Mock
    private VideocallRepository videocallRepo;
    @Mock
    private LanguageProfileRepository languageRepo;

    private Request request;
    private Profile volunteer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        request = new Request();
        request.setIdRequest(1);
        request.setDateRequest(LocalDateTime.now());
        request.setProfile(new Profile() {
            {
                setIdProfile(1);
            }
        });
        Skill skill = new Skill();
        skill.setIdSkill(99);
        request.setSkill(skill);

        volunteer = new Profile();
        volunteer.setIdProfile(2);
    }

    @Test
    void generateEmpairings_shouldCreateEmpairingsMatchingAllCriteria() {
        when(languageRepo.findLanguageIdsByProfile(1)).thenReturn(List.of(1, 2));
        when(availabilityRepo.findProfilesAvailableAtWithSkill(any(), any(), any()))
                .thenReturn(List.of(volunteer));
        when(empairingRepo.findProfileIdsByIdRequest(1)).thenReturn(Collections.emptyList());
        when(videocallRepo.findActiveVideocallsByProfile(2)).thenReturn(Collections.emptyList());
        when(languageRepo.findLanguageIdsByProfile(2)).thenReturn(List.of(2));

        List<Empairing> result = service.generateEmpairings(request);

        assertEquals(1, result.size());
        verify(empairingRepo, times(1)).save(any(Empairing.class));
    }

    @Test
    void generateEmpairings_shouldReturnEmptyWhenNoAvailableVolunteers() {
        when(availabilityRepo.findProfilesAvailableAtWithSkill(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        List<Empairing> result = service.generateEmpairings(request);

        assertTrue(result.isEmpty());
    }

    @Test
    void acceptEmpairing_shouldReturnNullIfAlreadyAccepted() {
        Request req = new Request();
        req.setIdRequest(1);
        Empairing emp = new Empairing();
        emp.setIdEmpairing(10);
        emp.setRequest(req);

        when(empairingRepo.findById(10)).thenReturn(Optional.of(emp));
        when(empairingRepo.existsByRequestIdRequestAndStateEmpairingTrue(1)).thenReturn(true);

        Videocall result = service.acceptEmpairing(10);

        assertNull(result);
    }

    @Test
    void acceptEmpairing_shouldAcceptIfNoPreviousAccepted() {
        Request req = new Request();
        req.setIdRequest(1);
        Empairing emp = new Empairing();
        emp.setIdEmpairing(10);
        emp.setRequest(req);

        when(empairingRepo.findById(10)).thenReturn(Optional.of(emp));
        when(empairingRepo.existsByRequestIdRequestAndStateEmpairingTrue(1)).thenReturn(false);

        Videocall call = new Videocall();
        when(videocallService.createVideocall(emp)).thenReturn(call);

        Videocall result = service.acceptEmpairing(10);

        assertNotNull(result);
        verify(empairingRepo).save(argThat(e -> e.getStateEmpairing()));
    }
}
