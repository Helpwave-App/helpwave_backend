package upc.helpwave.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.google.firebase.messaging.FirebaseMessaging;

import upc.helpwave.config.TestConfig;
import upc.helpwave.entities.Role;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.serviceimplements.JwtUserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
@TestPropertySource(properties = "firebase.enabled=false")
@TestPropertySource(locations = "classpath:application-test.properties")
public class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    private ApplicationContext context;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @BeforeEach
    void setup() {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "user", "pass", new ArrayList<>());

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtUserDetailsService.loadUserByUsername("user")).thenReturn(userDetails);

        Role role = new Role();
        role.setRol("VOLUNTEER");

        User user = new User();
        user.setIdUser(1);
        user.setUsername("user");
        user.setRole(role);

        when(userRepository.findByUsername("user")).thenReturn(user);
    }

    @DisplayName("Should expose AuthenticationManager bean")
    @Test
    void shouldExposeAuthenticationManagerBean() {
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
        assertThat(authenticationManager).isNotNull();
    }

    @DisplayName("Should expose PasswordEncoder bean")
    @Test
    void shouldExposePasswordEncoderBean() {
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        assertThat(passwordEncoder).isNotNull();
    }

    @DisplayName("Should allow access to public POST endpoint")
    @Test
    void shouldPermitPublicPost() throws Exception {
        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user\", \"password\":\"pass\"}"))
                .andExpect(status().isOk());
    }

    @DisplayName("Should allow access to public GET endpoint")
    @Test
    void shouldPermitPublicGet() throws Exception {
        mockMvc.perform(get("/skills"))
                .andExpect(status().isOk());
    }
}
