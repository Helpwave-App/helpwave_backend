package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.ProfileDTO;
import upc.helpwave.entities.Profile;
import upc.helpwave.serviceinterfaces.IProfileService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @Autowired
    private IProfileService pS;

    @PostMapping
    public void register(@RequestBody ProfileDTO dto) {
        ModelMapper m = new ModelMapper();
        Profile r = m.map(dto, Profile.class);
        pS.insert(r);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        pS.delete(id);
    }

    @GetMapping("/{id}")
    public ProfileDTO listId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        ProfileDTO dto = m.map(pS.listId(id), ProfileDTO.class);
        return dto;
    }

    @GetMapping
    public List<ProfileDTO> list() {
        return pS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, ProfileDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping
    public void update(@RequestBody ProfileDTO dto) {
        ModelMapper m = new ModelMapper();
        Profile a = m.map(dto, Profile.class);
        pS.insert(a);
    }
}
