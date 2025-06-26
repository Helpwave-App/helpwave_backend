package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.entities.Role;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.security.JwtRequest;
import upc.helpwave.security.JwtTokenUtil;
import upc.helpwave.serviceimplements.JwtUserDetailsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JwtAuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class JwtAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void authenticate_shouldReturnJwtResponse_whenCredentialsAreValid() throws Exception {
        // Arrange
        JwtRequest request = new JwtRequest("testuser", "password");
        String expectedToken = "mocked-jwt-token";
        String expectedRole = "ROLE_USER";
        int expectedId = 42;

        UserDetails mockUserDetails = mock(UserDetails.class);
        Authentication mockAuthentication = mock(Authentication.class);

        User mockUser = new User();
        mockUser.setIdUser(expectedId);
        Role role = new Role();
        role.setRol(expectedRole);
        mockUser.setRole(role);

        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(mockUserDetails);
        when(jwtTokenUtil.generateToken(mockUserDetails)).thenReturn(expectedToken);
        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);

        // Act + Assert
        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwttoken").value(expectedToken))
                .andExpect(jsonPath("$.role").value(expectedRole))
                .andExpect(jsonPath("$.idUser").value(expectedId));
    }

    @Test
    void authenticate_shouldReturnUnauthorized_whenBadCredentials() throws Exception {
        // Arrange
        JwtRequest request = new JwtRequest("wronguser", "wrongpass");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act + Assert
        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
