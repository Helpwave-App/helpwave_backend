package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import upc.helpwave.entities.Availability;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.AvailabilityRepository;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvailabilityServiceImplementTest {

    @InjectMocks
    private AvailabilityServiceImplement service;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insert_shouldCallRepositorySave() {
        Availability availability = new Availability();
        service.insert(availability);
        verify(availabilityRepository, times(1)).save(availability);
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        service.delete(1);
        verify(availabilityRepository, times(1)).deleteById(1);
    }

    @Test
    void listId_shouldReturnAvailabilityWhenExists() {
        Availability availability = new Availability();
        availability.setIdAvailability(1);
        when(availabilityRepository.findById(1)).thenReturn(Optional.of(availability));

        Availability result = service.listId(1);
        assertEquals(1, result.getIdAvailability());
    }

    @Test
    void listId_shouldReturnEmptyAvailabilityWhenNotExists() {
        when(availabilityRepository.findById(999)).thenReturn(Optional.empty());

        Availability result = service.listId(999);
        assertNotNull(result);
        assertEquals(0, result.getIdAvailability());
    }

    @Test
    void list_shouldReturnAllAvailabilities() {
        Availability a1 = new Availability();
        Availability a2 = new Availability();
        when(availabilityRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Availability> result = service.list();
        assertEquals(2, result.size());
    }

    @Test
    void insertAll_shouldCallRepositorySaveAll() {
        Availability a1 = new Availability();
        Availability a2 = new Availability();
        List<Availability> availabilities = Arrays.asList(a1, a2);

        service.insertAll(availabilities);
        verify(availabilityRepository, times(1)).saveAll(availabilities);
    }

    @Test
    void findByProfile_shouldReturnFilteredAvailability() {
        Profile profile = new Profile();
        profile.setIdProfile(101);

        Availability availability = new Availability(1, profile, "M", LocalTime.of(9, 0), LocalTime.of(12, 0));
        when(availabilityRepository.findByProfile(profile)).thenReturn(Collections.singletonList(availability));

        List<Availability> result = service.findByProfile(profile);
        assertEquals(1, result.size());
        assertEquals("M", result.get(0).getDay());
    }
}
