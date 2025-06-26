package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplementTest {

    @InjectMocks
    private UserServiceImplement userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insert_newUser_shouldSave() {
        // Arrange
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("123456");

        when(userRepository.buscarUsername("newUser")).thenReturn(0);
        when(passwordEncoder.encode("123456")).thenReturn("hashedPassword");

        // Act
        Integer result = userService.insert(user);

        // Assert
        assertEquals(0, result);
        assertEquals("hashedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void insert_existingUser_shouldNotSave() {
        // Arrange
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("123456");

        when(userRepository.buscarUsername("existingUser")).thenReturn(1);
        when(passwordEncoder.encode("123456")).thenReturn("hashedPassword");

        // Act
        Integer result = userService.insert(user);

        // Assert
        assertEquals(1, result);
        verify(userRepository, never()).save(user);
    }

    @Test
    public void delete_existingUser_shouldCallDeleteById() {
        // Arrange
        int idUser = 42;

        // Act
        userService.delete(idUser);

        // Assert
        verify(userRepository, times(1)).deleteById(idUser);
    }

    @Test
    public void listId_existingUser_shouldReturnUser() {
        // Arrange
        int idUser = 10;
        User userMock = new User();
        userMock.setIdUser(idUser);
        userMock.setUsername("testUser");

        when(userRepository.findById(idUser)).thenReturn(Optional.of(userMock));

        // Act
        User result = userService.listId(idUser);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void listId_nonExistingUser_shouldReturnEmptyUser() {
        // Arrange
        int idUser = 99;

        when(userRepository.findById(idUser)).thenReturn(Optional.empty());

        // Act
        User result = userService.listId(idUser);

        // Assert
        assertNotNull(result);
        assertNull(result.getIdUser());
    }
}
