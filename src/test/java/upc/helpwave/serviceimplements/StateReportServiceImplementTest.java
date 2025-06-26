package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import upc.helpwave.entities.StateReport;
import upc.helpwave.repositories.StateReportRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StateReportServiceImplementTest {

    @InjectMocks
    private StateReportServiceImplement service;

    @Mock
    private StateReportRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insert_shouldSaveStateReport() {
        StateReport state = new StateReport();
        service.insert(state);
        verify(repository).save(state);
    }

    @Test
    void delete_shouldDeleteStateReportById() {
        service.delete(1);
        verify(repository).deleteById(1);
    }

    @Test
    void listId_shouldReturnStateReportWhenExists() {
        StateReport expected = new StateReport();
        when(repository.findById(1)).thenReturn(Optional.of(expected));
        StateReport result = service.listId(1);
        assertEquals(expected, result);
    }

    @Test
    void listId_shouldReturnNewStateReportWhenNotExists() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        StateReport result = service.listId(1);
        assertNotNull(result);
    }

    @Test
    void list_shouldReturnAllStateReports() {
        List<StateReport> states = Arrays.asList(new StateReport(), new StateReport());
        when(repository.findAll()).thenReturn(states);
        List<StateReport> result = service.list();
        assertEquals(2, result.size());
    }
}
