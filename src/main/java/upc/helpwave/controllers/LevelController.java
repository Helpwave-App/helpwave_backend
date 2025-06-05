package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.LevelDTO;
import upc.helpwave.dtos.LevelProgressDTO;
import upc.helpwave.entities.Level;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.LevelRepository;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.serviceinterfaces.ILevelService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/levels")
public class LevelController {
    @Autowired
    private ILevelService lS;
    @Autowired
    private ProfileRepository pR;

    @Autowired
    private LevelRepository lR;

    @GetMapping("/progress/{idProfile}")
    public ResponseEntity<LevelProgressDTO> getLevelInfo(@PathVariable("idProfile") Integer idProfile) {
        Optional<Profile> profileOpt = pR.findById(idProfile);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = profileOpt.get();
        Level currentLevel = profile.getLevel();
        Integer assistances = profile.getAssistances() != null ? profile.getAssistances() : 0;
        BigDecimal score = profile.getScoreProfile() != null ? profile.getScoreProfile() : BigDecimal.ZERO;

        Optional<Level> nextLevelOpt = lR
                .findFirstByMinRequestGreaterThanOrderByMinRequestAsc(assistances);

        LevelProgressDTO dto = new LevelProgressDTO();
        dto.setAssistances(assistances);
        dto.setScoreProfile(score);
        dto.setCurrentLevel(currentLevel.getNameLevel());
        dto.setCurrentLevelPhotoUrl(currentLevel.getPhotoUrl());

        if (nextLevelOpt.isPresent()) {
            Level nextLevel = nextLevelOpt.get();
            dto.setMissingAssistances(nextLevel.getMinRequest() - assistances);
            dto.setNextLevel(nextLevel.getNameLevel());
            dto.setNextLevelPhotoUrl(nextLevel.getPhotoUrl());
        } else {
            dto.setMissingAssistances(0);
            dto.setNextLevel("Máximo nivel alcanzado");
            dto.setNextLevelPhotoUrl(currentLevel.getPhotoUrl());
        }

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public void register(@RequestBody LevelDTO dto) {
        ModelMapper m = new ModelMapper();
        Level r = m.map(dto, Level.class);
        lS.insert(r);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        lS.delete(id);
    }

    @GetMapping("/{id}")
    public LevelDTO listId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        LevelDTO dto = m.map(lS.listId(id), LevelDTO.class);
        return dto;
    }

    @GetMapping
    public List<LevelDTO> list() {
        return lS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, LevelDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping
    public void update(@RequestBody LevelDTO dto) {
        ModelMapper m = new ModelMapper();
        Level a = m.map(dto, Level.class);
        lS.insert(a);
    }
}
