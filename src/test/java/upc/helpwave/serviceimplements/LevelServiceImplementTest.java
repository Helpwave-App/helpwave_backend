package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import upc.helpwave.entities.Level;
import upc.helpwave.repositories.LevelRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LevelServiceImplementTest {

    @InjectMocks
    private LevelServiceImplement levelService;

    @Mock
    private LevelRepository levelRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insert_shouldSaveLevel() {
        Level level = new Level();
        levelService.insert(level);
        verify(levelRepository, times(1)).save(level);
    }

    @Test
    public void delete_shouldRemoveLevelById() {
        Integer id = 1;
        levelService.delete(id);
        verify(levelRepository, times(1)).deleteById(id);
    }

    @Test
    public void listId_shouldReturnLevelWhenExists() {
        Level level = new Level();
        when(levelRepository.findById(1)).thenReturn(Optional.of(level));
        Level result = levelService.listId(1);
        assertEquals(level, result);
    }

    @Test
    public void listId_shouldReturnEmptyLevelWhenNotExists() {
        when(levelRepository.findById(1)).thenReturn(Optional.empty());
        Level result = levelService.listId(1);
        assertNotNull(result);
    }

    @Test
    public void list_shouldReturnAllLevels() {
        List<Level> levels = Arrays.asList(new Level(), new Level());
        when(levelRepository.findAll()).thenReturn(levels);
        List<Level> result = levelService.list();
        assertEquals(2, result.size());
    }
}
