package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
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
    private ILanguageProfileService lS;
    @Autowired
    private UserRepository uR;
    @Autowired
    private ProfileRepository pR;

    @Autowired
    private LanguageRepository lR;

    @PostMapping("/batch")
    public void registerBatch(@RequestBody LanguageProfileListDTO dto) {
        Optional<Profile> profileOpt = pR.findById(dto.getIdProfile());
        if (!profileOpt.isPresent()) {
            throw new RuntimeException("Perfil no encontrado con ID: " + dto.getIdProfile());
        }

        Profile profile = profileOpt.get();

        List<LanguageProfile> list = dto.getLanguageIds().stream().map(languageId -> {
            Optional<Language> languageOpt = lR.findById(languageId);
            if (!languageOpt.isPresent()) {
                throw new RuntimeException("Language no encontrado con ID: " + languageId);
            }
            LanguageProfile sp = new LanguageProfile();
            sp.setProfile(profile);
            sp.setLanguage(languageOpt.get());
            return sp;
        }).collect(Collectors.toList());

        lS.insertAll(list);
    }
    @PostMapping
    public void register(@RequestBody LanguageProfileDTO dto){
        ModelMapper m=new ModelMapper();
        LanguageProfile r=m.map(dto, LanguageProfile.class);
        lS.insert(r);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id)
    {
        lS.delete(id);
    }
    @GetMapping("/{id}")
    public LanguageProfileDTO listId(@PathVariable("id")Integer id){
        ModelMapper m=new ModelMapper();
        LanguageProfileDTO dto=m.map(lS.listId(id),LanguageProfileDTO.class);
        return dto;
    }
    @GetMapping
    public List<LanguageProfileDTO> list(){
        return lS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,LanguageProfileDTO.class);
        }).collect(Collectors.toList());
    }
    @PutMapping
    public void update(@RequestBody LanguageProfileDTO dto) {
        ModelMapper m = new ModelMapper();
        LanguageProfile a=m.map(dto, LanguageProfile.class);
        lS.insert(a);
    }
    @GetMapping("/user/{idUser}")
    public List<LanguageProfileDTO> listByUser(@PathVariable("idUser") int idUser) {
        Optional<User> userOpt = uR.findById(idUser);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUser);
        }

        Profile profile = userOpt.get().getProfile();
        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado.");
        }

        List<LanguageProfile> languageProfiles = lS.findByProfile(profile);

        ModelMapper m = new ModelMapper();
        return languageProfiles.stream()
                .map(sp -> {
                    LanguageProfileDTO dto = new LanguageProfileDTO();
                    dto.setIdLanguageProfile(sp.getIdLanguageProfile());
                    dto.setIdProfile(sp.getProfile().getIdProfile());
                    dto.setIdLanguage(sp.getLanguage().getIdLanguage());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @PutMapping("/profile/{id}")
    public void updateByProfile(@PathVariable("id") Integer profileId, @RequestBody LanguageProfileDTO dto) {
        ModelMapper m = new ModelMapper();
        LanguageProfile languageProfile = m.map(dto, LanguageProfile.class);

        Profile profile = new Profile();
        profile.setIdProfile(profileId);
        languageProfile.setProfile(profile);

        lS.insert(languageProfile);
    }
}
