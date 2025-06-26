package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import upc.helpwave.entities.Role;
import upc.helpwave.repositories.RoleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceImplementTest {

    @InjectMocks
    private RoleServiceImplement roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insert_validRole_shouldCallSaveOnce() {
        // Arrange
        Role role = new Role();
        role.setIdRole(1L);
        role.setRol("VOLUNTEER");

        // Act
        roleService.insert(role);

        // Assert
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void delete_existingId_shouldCallDeleteByIdOnce() {
        // Arrange
        Long id = 1L;

        // Act
        roleService.delete(id);

        // Assert
        verify(roleRepository, times(1)).deleteById(id);
    }

    @Test
    public void list_shouldReturnAllRoles() {
        // Arrange
        Role volunteer = new Role();
        volunteer.setIdRole(1L);
        volunteer.setRol("volunteer");

        Role requester = new Role();
        requester.setIdRole(2L);
        requester.setRol("requester");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(volunteer, requester));

        // Act
        List<Role> result = roleService.list();

        // Assert
        assertEquals(2, result.size());
        assertEquals("volunteer", result.get(0).getRole());
        assertEquals("requester", result.get(1).getRole());
    }

    @Test
    public void listId_existingId_shouldReturnRole() {
        // Arrange
        Role volunteer = new Role();
        volunteer.setIdRole(1L);
        volunteer.setRol("volunteer");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(volunteer));

        // Act
        Role result = roleService.listId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdRole());
        assertEquals("volunteer", result.getRole());
    }

    @Test
    public void listId_nonExistingId_shouldReturnEmptyRole() {
        // Arrange
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Role result = roleService.listId(99L);

        // Assert
        assertNotNull(result);
        assertNull(result.getIdRole());
    }
}
