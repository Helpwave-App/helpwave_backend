package upc.helpwave.serviceimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import upc.helpwave.entities.Role;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUserDetailsServiceTest {

    @InjectMocks
    private JwtUserDetailsService service;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_shouldThrowWhenUserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("unknown"));
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("secret");
        user.setState(true);
        Role role = new Role();
        role.setRol("ROLE_USER");
        user.setRole(role);

        when(userRepository.findByUsername("john")).thenReturn(user);

        UserDetails details = service.loadUserByUsername("john");

        assertEquals("john", details.getUsername());
        assertEquals("secret", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }
}
