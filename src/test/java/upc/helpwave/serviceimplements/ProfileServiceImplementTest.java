package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.ProfileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceImplementTest {

    @InjectMocks
    private ProfileServiceImplement profileService;

    @Mock
    private ProfileRepository profileRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insert_shouldCallSave() {
        // Arrange
        Profile profile = new Profile();
        profile.setIdProfile(1);
        profile.setName("Test");

        // Act
        profileService.insert(profile);

        // Assert
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    public void delete_shouldCallDeleteById() {
        // Arrange
        int id = 1;

        // Act
        profileService.delete(id);

        // Assert
        verify(profileRepository, times(1)).deleteById(id);
    }

    @Test
    public void listId_existingProfile_shouldReturnProfile() {
        // Arrange
        int id = 5;
        Profile profile = new Profile();
        profile.setIdProfile(id);
        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));

        // Act
        Profile result = profileService.listId(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getIdProfile());
    }

    @Test
    public void listId_nonExistingProfile_shouldReturnEmptyProfile() {
        // Arrange
        int id = 99;
        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Profile result = profileService.listId(id);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getIdProfile()); // Asume ID por defecto = 0
    }

    @Test
    public void list_shouldReturnAllProfiles() {
        // Arrange
        Profile p1 = new Profile();
        Profile p2 = new Profile();
        when(profileRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // Act
        List<Profile> result = profileService.list();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void findById_existingProfile_shouldReturnOptional() {
        // Arrange
        int id = 3;
        Profile profile = new Profile();
        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));

        // Act
        Optional<Profile> result = profileService.findById(id);

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    public void findById_nonExistingProfile_shouldReturnEmptyOptional() {
        // Arrange
        int id = 404;
        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Profile> result = profileService.findById(id);

        // Assert
        assertFalse(result.isPresent());
    }
}
