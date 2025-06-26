package upc.helpwave.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import upc.helpwave.dtos.RoleDTO;
import upc.helpwave.entities.Role;
import upc.helpwave.security.JwtRequestFilter;
import upc.helpwave.serviceinterfaces.IRoleService;
import upc.helpwave.serviceimplements.JwtUserDetailsService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRoleService roleService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldCallInsert() throws Exception {
        RoleDTO dto = new RoleDTO();
        dto.setRole("volunteer");

        doNothing().when(roleService).insert(any(Role.class));

        mockMvc.perform(post("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldCallDelete() throws Exception {
        doNothing().when(roleService).delete(1L);

        mockMvc.perform(delete("/roles/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void listId_shouldReturnRoleDTO() throws Exception {
        Role role = new Role();
        role.setIdRole(1L);
        role.setRol("requester");

        when(roleService.listId(1L)).thenReturn(role);

        mockMvc.perform(get("/roles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRole").value(1))
                .andExpect(jsonPath("$.role").value("requester"));
    }

    @Test
    void list_shouldReturnListOfRoles() throws Exception {
        Role role1 = new Role();
        role1.setIdRole(1L);
        role1.setRol("volunteer");

        Role role2 = new Role();
        role2.setIdRole(2L);
        role2.setRol("requester");

        when(roleService.list()).thenReturn(List.of(role1, role2));

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idRole").value(1))
                .andExpect(jsonPath("$[0].role").value("volunteer"))
                .andExpect(jsonPath("$[1].idRole").value(2))
                .andExpect(jsonPath("$[1].role").value("requester"));
    }

    @Test
    void update_shouldCallInsert() throws Exception {
        RoleDTO dto = new RoleDTO();
        dto.setIdRole(1L);
        dto.setRole("volunteer");

        doNothing().when(roleService).insert(any(Role.class));

        mockMvc.perform(put("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
