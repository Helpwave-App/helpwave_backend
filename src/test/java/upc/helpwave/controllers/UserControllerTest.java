package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.UserDTO;
import upc.helpwave.entities.Level;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.User;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceimplements.JwtUserDetailsService;
import upc.helpwave.serviceinterfaces.IUserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldReturnRegisterResponse() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("testuser");
        Profile profile = new Profile();
        profile.setIdProfile(100);
        dto.setProfile(profile);

        User mappedUser = new User();
        mappedUser.setIdUser(1);
        Profile p = new Profile();
        p.setIdProfile(100);
        Level defaultLevel = new Level();
        defaultLevel.setIdLevel(1);
        p.setLevel(defaultLevel);
        p.setUser(mappedUser);
        mappedUser.setProfile(p);

        when(userService.insert(any(User.class))).thenAnswer(invocation -> {
            User argUser = invocation.getArgument(0);
            argUser.setIdUser(1);
            return 0;
        });

        String jsonRequest = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUser").value(1))
                .andExpect(jsonPath("$.idProfile").value(100))
                .andExpect(jsonPath("$.message").value("Usuario registrado correctamente."));
    }

    @Test
    void delete_shouldCallDelete() throws Exception {
        doNothing().when(userService).delete(1);

        mockMvc.perform(delete("/user/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void listId_shouldReturnUserDTO() throws Exception {
        User user = new User();
        user.setIdUser(1);
        user.setUsername("testuser");
        Profile profile = new Profile();
        profile.setIdProfile(100);
        user.setProfile(profile);

        when(userService.listId(1)).thenReturn(user);

        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUser").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.profile.idProfile").value(100));
    }

    @Test
    void checkUsername_shouldReturnTrue() throws Exception {
        when(userService.existsByUsername("testuser")).thenReturn(true);

        mockMvc.perform(get("/user/check-username")
                .param("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void checkUsername_shouldReturnFalse() throws Exception {
        when(userService.existsByUsername("nouser")).thenReturn(false);

        mockMvc.perform(get("/user/check-username")
                .param("username", "nouser"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void update_shouldCallInsert() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setIdUser(1);
        dto.setUsername("updateduser");

        when(userService.insert(any(User.class))).thenReturn(0);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void list_shouldReturnListOfUserDTO() throws Exception {
        User user1 = new User();
        user1.setIdUser(1);
        user1.setUsername("user1");
        Profile profile1 = new Profile();
        profile1.setIdProfile(10);
        user1.setProfile(profile1);

        User user2 = new User();
        user2.setIdUser(2);
        user2.setUsername("user2");
        Profile profile2 = new Profile();
        profile2.setIdProfile(20);
        user2.setProfile(profile2);

        when(userService.list()).thenReturn(java.util.List.of(user1, user2));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idUser").value(1))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].profile.idProfile").value(10))
                .andExpect(jsonPath("$[1].idUser").value(2))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].profile.idProfile").value(20));
    }

}
