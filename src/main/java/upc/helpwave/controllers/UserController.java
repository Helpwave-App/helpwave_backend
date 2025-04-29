package upc.helpwave.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import upc.helpwave.dtos.UserDTO;
import upc.helpwave.entities.User;
import upc.helpwave.serviceinterfaces.IUserService;

import java.util.List;

import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Role;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService uS;

    @Autowired
    private PasswordEncoder bcrypt;

    @PostMapping("/register")
    public void registrar(@RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();

        User u = m.map(dto, User.class);

        Role role = new Role();
        role.setIdRole(dto.getIdRole());

        Profile profile = m.map(dto.getProfile(), Profile.class);
        u.setProfile(profile);

        u.setRole(role);
        uS.insert(u);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") int idUser) {
        uS.delete(idUser);
    }

    @GetMapping("/{id}")
    public UserDTO listarId(@PathVariable("id") int idUser) {
        ModelMapper m = new ModelMapper();
        UserDTO dto = m.map(uS.listId(idUser), UserDTO.class);
        return dto;
    }

    @PutMapping
    public void modificar(@RequestBody UserDTO dto) {
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
    public List<UserDTO> listar() {
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
