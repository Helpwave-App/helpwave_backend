package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import upc.helpwave.entities.TypeReport;
import upc.helpwave.repositories.TypeReportRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TypeReportServiceImplementTest {

    @InjectMocks
    private TypeReportServiceImplement service;

    @Mock
    private TypeReportRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insert_shouldSaveTypeReport() {
        TypeReport typeReport = new TypeReport();
        service.insert(typeReport);
        verify(repository).save(typeReport);
    }

    @Test
    void delete_shouldDeleteTypeReportById() {
        service.delete(1);
        verify(repository).deleteById(1);
    }

    @Test
    void listId_shouldReturnTypeReportWhenExists() {
        TypeReport expected = new TypeReport();
        when(repository.findById(1)).thenReturn(Optional.of(expected));
        TypeReport result = service.listId(1);
        assertEquals(expected, result);
    }

    @Test
    void listId_shouldReturnNewTypeReportWhenNotExists() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        TypeReport result = service.listId(1);
        assertNotNull(result);
    }

    @Test
    void list_shouldReturnAllTypeReports() {
        List<TypeReport> reports = Arrays.asList(new TypeReport(), new TypeReport());
        when(repository.findAll()).thenReturn(reports);
        List<TypeReport> result = service.list();
        assertEquals(2, result.size());
    }
}
