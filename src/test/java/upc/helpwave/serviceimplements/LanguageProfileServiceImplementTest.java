package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import upc.helpwave.entities.LanguageProfile;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.LanguageProfileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LanguageProfileServiceImplementTest {

    @InjectMocks
    private LanguageProfileServiceImplement service;

    @Mock
    private LanguageProfileRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insert_shouldSaveLanguageProfile() {
        LanguageProfile lp = new LanguageProfile();
        service.insert(lp);
        verify(repository, times(1)).save(lp);
    }

    @Test
    public void delete_shouldDeleteLanguageProfileById() {
        service.delete(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void listId_shouldReturnLanguageProfileWhenExists() {
        LanguageProfile expected = new LanguageProfile();
        when(repository.findById(1)).thenReturn(Optional.of(expected));
        LanguageProfile result = service.listId(1);
        assertEquals(expected, result);
    }

    @Test
    public void listId_shouldReturnNewLanguageProfileWhenNotExists() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        LanguageProfile result = service.listId(1);
        assertNotNull(result);
    }

    @Test
    public void list_shouldReturnAllLanguageProfiles() {
        List<LanguageProfile> list = Arrays.asList(new LanguageProfile(), new LanguageProfile());
        when(repository.findAll()).thenReturn(list);
        List<LanguageProfile> result = service.list();
        assertEquals(2, result.size());
    }

    @Test
    public void insertAll_shouldSaveAllLanguageProfiles() {
        List<LanguageProfile> list = Arrays.asList(new LanguageProfile(), new LanguageProfile());
        service.insertAll(list);
        verify(repository, times(1)).saveAll(list);
    }

    @Test
    public void findByProfile_shouldReturnLanguageProfilesForProfile() {
        Profile profile = new Profile();
        List<LanguageProfile> list = Arrays.asList(new LanguageProfile());
        when(repository.findByProfile(profile)).thenReturn(list);
        List<LanguageProfile> result = service.findByProfile(profile);
        assertEquals(list, result);
    }
}
