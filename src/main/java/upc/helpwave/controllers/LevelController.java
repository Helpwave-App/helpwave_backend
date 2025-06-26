package upc.helpwave.controllers;

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
    private ILevelService levelService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private LevelRepository levelRepository;

    @PostMapping
    public void register(@RequestBody LevelDTO dto) {
        levelService.insert(fromDto(dto));
    }

    @PutMapping
    public void update(@RequestBody LevelDTO dto) {
        levelService.insert(fromDto(dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        levelService.delete(id);
    }

    @GetMapping("/{id}")
    public LevelDTO listId(@PathVariable("id") Integer id) {
        Level level = levelService.listId(id);
        return toDto(level);
    }

    @GetMapping
    public List<LevelDTO> list() {
        return levelService.list().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/progress/{idProfile}")
    public ResponseEntity<LevelProgressDTO> getLevelInfo(@PathVariable("idProfile") Integer idProfile) {
        Optional<Profile> profileOpt = profileRepository.findById(idProfile);
        if (profileOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = profileOpt.get();
        Level currentLevel = profile.getLevel();
        Integer assistances = Optional.ofNullable(profile.getAssistances()).orElse(0);
        BigDecimal score = Optional.ofNullable(profile.getScoreProfile()).orElse(BigDecimal.ZERO);

        Optional<Level> nextLevelOpt = levelRepository
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

    private LevelDTO toDto(Level entity) {
        LevelDTO dto = new LevelDTO();
        dto.setIdLevel(entity.getIdLevel());
        dto.setNameLevel(entity.getNameLevel());
        dto.setMinRequest(entity.getMinRequest());
        dto.setMaxRequest(entity.getMaxRequest());
        dto.setPhotoUrl(entity.getPhotoUrl());
        return dto;
    }

    private Level fromDto(LevelDTO dto) {
        Level entity = new Level();
        entity.setIdLevel(dto.getIdLevel());
        entity.setNameLevel(dto.getNameLevel());
        entity.setMinRequest(dto.getMinRequest());
        entity.setMaxRequest(dto.getMaxRequest());
        entity.setPhotoUrl(dto.getPhotoUrl());
        return entity;
    }
}
