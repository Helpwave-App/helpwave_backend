package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import upc.helpwave.entities.Language;
import upc.helpwave.repositories.LanguageRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LanguageServiceImplementTest {

    @InjectMocks
    private LanguageServiceImplement service;

    @Mock
    private LanguageRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insert_shouldSaveLanguage() {
        Language language = new Language();
        service.insert(language);
        verify(repository, times(1)).save(language);
    }

    @Test
    public void delete_shouldDeleteLanguageById() {
        service.delete(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void listId_shouldReturnLanguageWhenExists() {
        Language expected = new Language();
        when(repository.findById(1)).thenReturn(Optional.of(expected));
        Language result = service.listId(1);
        assertEquals(expected, result);
    }

    @Test
    public void listId_shouldReturnNewLanguageWhenNotExists() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        Language result = service.listId(1);
        assertNotNull(result);
    }

    @Test
    public void list_shouldReturnAllLanguages() {
        List<Language> languages = Arrays.asList(new Language(), new Language());
        when(repository.findAll()).thenReturn(languages);
        List<Language> result = service.list();
        assertEquals(2, result.size());
    }
}
