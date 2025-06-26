package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import upc.helpwave.entities.Reports;
import upc.helpwave.repositories.ReportsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceImplementTest {

    @InjectMocks
    private ReportServiceImplement service;

    @Mock
    private ReportsRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insert_shouldSaveReport() {
        Reports report = new Reports();
        service.insert(report);
        verify(repository).save(report);
    }

    @Test
    void delete_shouldDeleteReportById() {
        service.delete(1);
        verify(repository).deleteById(1);
    }

    @Test
    void listId_shouldReturnReportWhenExists() {
        Reports expected = new Reports();
        when(repository.findById(1)).thenReturn(Optional.of(expected));
        Reports result = service.listId(1);
        assertEquals(expected, result);
    }

    @Test
    void listId_shouldReturnNewReportWhenNotExists() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        Reports result = service.listId(1);
        assertNotNull(result);
    }

    @Test
    void list_shouldReturnAllReports() {
        List<Reports> reports = Arrays.asList(new Reports(), new Reports());
        when(repository.findAll()).thenReturn(reports);
        List<Reports> result = service.list();
        assertEquals(2, result.size());
    }
}
