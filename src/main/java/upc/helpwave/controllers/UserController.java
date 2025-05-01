package upc.helpwave.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import upc.helpwave.dtos.RegisterResponseDTO;
import upc.helpwave.dtos.UserDTO;
import upc.helpwave.entities.User;
import upc.helpwave.serviceinterfaces.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService uS;
    @Autowired
    private PasswordEncoder bcrypt;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();
        User u = m.map(dto, User.class);
        uS.insert(u);

        int idUser = u.getIdUser();
        int idProfile = u.getProfile() != null ? u.getProfile().getIdProfile() : 0;

        RegisterResponseDTO response = new RegisterResponseDTO(idUser, idProfile, "Usuario registrado correctamente.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int idUser) {
        uS.delete(idUser);
    }

    @GetMapping("/{id}")
    public UserDTO listId(@PathVariable("id") int idUser) {
        ModelMapper m = new ModelMapper();
        UserDTO dto = m.map(uS.listId(idUser), UserDTO.class);
        return dto;
    }

    @GetMapping("/check-username")
    public boolean checkUsername(@RequestParam("username") String username) {
        return uS.existsByUsername(username);
    }

    @PutMapping
    public void update(@RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();
        User u = m.map(dto, User.class);
        uS.insert(u);
    }

    @GetMapping("/list")
    public String listUser(Model model) {
        try {
            model.addAttribute("user", new User());
            model.addAttribute("listaUsuarios", uS.list());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "usersecurity/listUser";
    }

    @GetMapping
    public List<UserDTO> list() {
        return uS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public String saveUser(@Valid User user, BindingResult result, Model model, SessionStatus status) throws Exception {
        if (result.hasErrors()) {
            return "usersecurity/user";
        } else {
            if (user.getPassword() == null) {
                model.addAttribute("error", "La contraseña no puede ser nula.");
                return "usersecurity/user";
            }
            String bcryptPassword = bcrypt.encode(user.getPassword());
            user.setPassword(bcryptPassword);
            int rpta = uS.insert(user);
            if (rpta > 0) {
                model.addAttribute("mensaje", "Ya existe");
                return "usersecurity/user";
            } else {
                model.addAttribute("mensaje", "Se guardó correctamente");
                status.setComplete();
            }
        }
        model.addAttribute("listaUsuarios", uS.list());
        return "usersecurity/listUser";
    }
}