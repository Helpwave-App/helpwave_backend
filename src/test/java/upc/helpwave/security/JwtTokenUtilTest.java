package upc.helpwave.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    // Helper method to set private fields via reflection
    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        // Secret must match the one used in production/test
        setField(jwtTokenUtil, "secret", "mySecretKey1234567890");
    }

    @Test
    void shouldGenerateAndValidateTokenSuccessfully() {
        User user = new User("testuser", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtTokenUtil.generateToken(user);

        assertNotNull(token);
        assertEquals("testuser", jwtTokenUtil.getUsernameFromToken(token));
        assertFalse(jwtTokenUtil.getExpirationDateFromToken(token).before(new Date()));
        assertTrue(jwtTokenUtil.validateToken(token, user));
    }

    @Test
    void shouldDetectExpiredToken() throws InterruptedException {
        JwtTokenUtil shortLivedUtil = new JwtTokenUtil();
        setField(shortLivedUtil, "secret", "mySecretKey1234567890");
        shortLivedUtil.setTokenValidity(1); // solo 1 milisegundo de vida útil

        User user = new User("testuser", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        String token = shortLivedUtil.generateToken(user);

        Thread.sleep(10); // Esperamos un poco para que expire
        assertFalse(shortLivedUtil.validateToken(token, user));
    }

}
