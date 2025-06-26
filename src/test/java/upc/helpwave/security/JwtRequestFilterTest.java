package upc.helpwave.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import upc.helpwave.serviceimplements.JwtUserDetailsService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtRequestFilterTest {

    private JwtRequestFilter filter;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();

        // Inyectar valor secreto manualmente por reflexión
        try {
            java.lang.reflect.Field secretField = JwtTokenUtil.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwtTokenUtil, "mySecretKey1234567890"); // Este valor debe ser suficientemente largo
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        userDetailsService = Mockito.mock(JwtUserDetailsService.class);

        filter = new JwtRequestFilter();

        // Inyectar dependencias por reflexión
        try {
            java.lang.reflect.Field tokenUtilField = JwtRequestFilter.class.getDeclaredField("jwtTokenUtil");
            tokenUtilField.setAccessible(true);
            tokenUtilField.set(filter, jwtTokenUtil);

            java.lang.reflect.Field userDetailsServiceField = JwtRequestFilter.class
                    .getDeclaredField("jwtUserDetailsService");
            userDetailsServiceField.setAccessible(true);
            userDetailsServiceField.set(filter, userDetailsService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldSetAuthenticationForValidToken() throws Exception {
        User user = new User("testuser", "pass", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtTokenUtil.generateToken(user);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn("/secure/endpoint");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(user);

        filter.doFilterInternal(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testuser",
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }
}
