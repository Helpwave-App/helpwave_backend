package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import upc.helpwave.entities.Skill;
import upc.helpwave.repositories.SkillRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SkillServiceImplementTest {

    private SkillServiceImplement service;
    private SkillRepository repository;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(SkillRepository.class);
        service = new SkillServiceImplement(repository);
    }

    @Test
    public void insert_shouldSaveSkill() {
        Skill skill = new Skill();
        service.insert(skill);
        verify(repository, times(1)).save(skill);
    }

    @Test
    public void delete_shouldDeleteSkillById() {
        service.delete(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void listId_shouldReturnSkillWhenExists() {
        Skill expected = new Skill();
        expected.setIdSkill(1);
        when(repository.findById(1)).thenReturn(Optional.of(expected));

        Skill result = service.listId(1);
        assertEquals(1, result.getIdSkill());
    }

    @Test
    public void listId_shouldReturnEmptySkillWhenNotExists() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        Skill result = service.listId(999);
        assertNotNull(result);
        assertEquals(0, result.getIdSkill());
    }

    @Test
    public void list_shouldReturnAllSkills() {
        List<Skill> list = Arrays.asList(new Skill(), new Skill());
        when(repository.findAll()).thenReturn(list);

        List<Skill> result = service.list();
        assertEquals(2, result.size());
    }
}
