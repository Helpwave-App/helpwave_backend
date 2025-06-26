package upc.helpwave.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.LanguageProfileDTO;
import upc.helpwave.dtos.LanguageProfileListDTO;
import upc.helpwave.entities.*;
import upc.helpwave.repositories.LanguageRepository;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.serviceinterfaces.ILanguageProfileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/languageProfiles")
public class LanguageProfileController {

    @Autowired
    private ILanguageProfileService languageProfileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @PostMapping("/batch")
    public void registerBatch(@RequestBody LanguageProfileListDTO dto) {
        Profile profile = profileRepository.findById(dto.getIdProfile())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + dto.getIdProfile()));

        List<LanguageProfile> languageProfiles = dto.getLanguageIds().stream()
                .map(languageId -> {
                    Language language = languageRepository.findById(languageId)
                            .orElseThrow(() -> new RuntimeException("Idioma no encontrado con ID: " + languageId));
                    LanguageProfile lp = new LanguageProfile();
                    lp.setProfile(profile);
                    lp.setLanguage(language);
                    return lp;
                }).collect(Collectors.toList());

        languageProfileService.insertAll(languageProfiles);
    }

    @PostMapping
    public void register(@RequestBody LanguageProfileDTO dto) {
        LanguageProfile lp = new LanguageProfile();
        lp.setIdLanguageProfile(dto.getIdLanguageProfile());

        Language language = new Language();
        language.setIdLanguage(dto.getIdLanguage());
        lp.setLanguage(language);

        Profile profile = new Profile();
        profile.setIdProfile(dto.getIdProfile());
        lp.setProfile(profile);

        languageProfileService.insert(lp);
    }

    @PutMapping
    public void update(@RequestBody LanguageProfileDTO dto) {
        register(dto);
    }

    @PutMapping("/profile/{id}")
    public void updateByProfile(@PathVariable("id") Integer profileId, @RequestBody LanguageProfileDTO dto) {
        LanguageProfile lp = new LanguageProfile();
        lp.setIdLanguageProfile(dto.getIdLanguageProfile());

        Language language = new Language();
        language.setIdLanguage(dto.getIdLanguage());
        lp.setLanguage(language);

        Profile profile = new Profile();
        profile.setIdProfile(profileId);
        lp.setProfile(profile);

        languageProfileService.insert(lp);
    }

    @GetMapping("/{id}")
    public LanguageProfileDTO listId(@PathVariable("id") Integer id) {
        LanguageProfile lp = languageProfileService.listId(id);
        return toDto(lp);
    }

    @GetMapping
    public List<LanguageProfileDTO> list() {
        return languageProfileService.list().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{idUser}")
    public List<LanguageProfileDTO> listByUser(@PathVariable("idUser") int idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUser));

        Profile profile = Optional.ofNullable(user.getProfile())
                .orElseThrow(() -> new RuntimeException("El usuario no tiene un perfil asociado."));

        return languageProfileService.findByProfile(profile).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        languageProfileService.delete(id);
    }

    private LanguageProfileDTO toDto(LanguageProfile entity) {
        LanguageProfileDTO dto = new LanguageProfileDTO();
        dto.setIdLanguageProfile(entity.getIdLanguageProfile());
        dto.setIdLanguage(entity.getLanguage().getIdLanguage());
        dto.setIdProfile(entity.getProfile().getIdProfile());
        return dto;
    }
}
