package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.SkillProfile;
import upc.helpwave.repositories.SkillProfileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SkillProfileServiceImplementTest {

    private SkillProfileServiceImplement service;
    private SkillProfileRepository repository;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(SkillProfileRepository.class);
        service = new SkillProfileServiceImplement(repository);
    }

    @Test
    public void insert_shouldSaveSkillProfile() {
        SkillProfile skillProfile = new SkillProfile();
        service.insert(skillProfile);
        verify(repository, times(1)).save(skillProfile);
    }

    @Test
    public void delete_shouldDeleteSkillProfileById() {
        service.delete(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void listId_shouldReturnSkillProfileWhenExists() {
        SkillProfile expected = new SkillProfile();
        expected.setIdSkillProfile(1);
        when(repository.findById(1)).thenReturn(Optional.of(expected));

        SkillProfile result = service.listId(1);
        assertEquals(1, result.getIdSkillProfile());
    }

    @Test
    public void listId_shouldReturnEmptySkillProfileWhenNotExists() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        SkillProfile result = service.listId(999);
        assertNotNull(result);
        assertEquals(0, result.getIdSkillProfile());
    }

    @Test
    public void list_shouldReturnAllSkillProfiles() {
        List<SkillProfile> list = Arrays.asList(new SkillProfile(), new SkillProfile());
        when(repository.findAll()).thenReturn(list);

        List<SkillProfile> result = service.list();
        assertEquals(2, result.size());
    }

    @Test
    public void insertAll_shouldSaveAllSkillProfiles() {
        List<SkillProfile> skillProfiles = Arrays.asList(new SkillProfile(), new SkillProfile());
        service.insertAll(skillProfiles);
        verify(repository, times(1)).saveAll(skillProfiles);
    }

    @Test
    public void findByProfile_shouldReturnSkillProfilesByProfile() {
        Profile profile = new Profile();
        profile.setIdProfile(1);
        List<SkillProfile> list = Arrays.asList(new SkillProfile(), new SkillProfile());
        when(repository.findByProfile(profile)).thenReturn(list);

        List<SkillProfile> result = service.findByProfile(profile);
        assertEquals(2, result.size());
    }
}
