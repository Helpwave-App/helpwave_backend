package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.ProfileDTO;
import upc.helpwave.entities.Level;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.LevelRepository;
import upc.helpwave.serviceinterfaces.IProfileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @Autowired
    private IProfileService pS;
    @Autowired
    private LevelRepository lR;

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

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePartially(@PathVariable("id") Integer id, @RequestBody ProfileDTO dto) {
        Optional<Profile> existingOpt = pS.findById(id);

        if (!existingOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Profile existing = existingOpt.get();

        if (dto.getIdLevel() != null) {
            Optional<Level> levelOpt = lR.findById(dto.getIdLevel());
            if (!levelOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Nivel no encontrado.");
            }
            existing.setLevel(levelOpt.get());
        }
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
        if (dto.getBirthDate() != null) existing.setBirthDate(dto.getBirthDate());
        if (dto.getScoreProfile() != null) existing.setScoreProfile(dto.getScoreProfile());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) existing.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getPhotoUrl() != null) existing.setPhotoUrl(dto.getPhotoUrl());
        if (dto.getAssistances() != null) existing.setAssistances(dto.getAssistances());

        pS.insert(existing);

        return ResponseEntity.ok("Perfil actualizado parcialmente");
    }
}
