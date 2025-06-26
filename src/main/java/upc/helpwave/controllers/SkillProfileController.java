package upc.helpwave.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upc.helpwave.dtos.SkillProfileDTO;
import upc.helpwave.dtos.SkillProfileListDTO;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Skill;
import upc.helpwave.entities.SkillProfile;
import upc.helpwave.entities.User;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.repositories.SkillRepository;
import upc.helpwave.repositories.UserRepository;
import upc.helpwave.serviceinterfaces.ISkillProfileService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skillProfiles")
public class SkillProfileController {

    @Autowired
    private ISkillProfileService spS;

    @Autowired
    private ProfileRepository pR;

    @Autowired
    private SkillRepository sR;

    @Autowired
    private UserRepository uR;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();

            modelMapper.createTypeMap(SkillProfile.class, SkillProfileDTO.class).addMappings(mapper -> {
                mapper.map(src -> src.getProfile().getIdProfile(), SkillProfileDTO::setIdProfile);
                mapper.map(SkillProfile::getIdSkillProfile, SkillProfileDTO::setIdSkillProfile);
                mapper.map(src -> src.getSkill().getIdSkill(), SkillProfileDTO::setIdSkill);
            });
        }
    }

    @PostMapping("/batch")
    public void registerBatch(@RequestBody SkillProfileListDTO dto) {
        Optional<Profile> profileOpt = pR.findById(dto.getIdProfile());
        if (!profileOpt.isPresent()) {
            throw new RuntimeException("Perfil no encontrado con ID: " + dto.getIdProfile());
        }

        Profile profile = profileOpt.get();

        List<SkillProfile> list = dto.getSkillIds().stream().map(skillId -> {
            Optional<Skill> skillOpt = sR.findById(skillId);
            if (!skillOpt.isPresent()) {
                throw new RuntimeException("Skill no encontrado con ID: " + skillId);
            }
            SkillProfile sp = new SkillProfile();
            sp.setProfile(profile);
            sp.setSkill(skillOpt.get());
            return sp;
        }).collect(Collectors.toList());

        spS.insertAll(list);
    }

    @PostMapping
    public void register(@RequestBody SkillProfileDTO dto) {
        SkillProfile sp = modelMapper.map(dto, SkillProfile.class);
        spS.insert(sp);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        spS.delete(id);
    }

    @GetMapping("/{id}")
    public SkillProfileDTO listId(@PathVariable("id") Integer id) {
        SkillProfile sp = spS.listId(id);
        return modelMapper.map(sp, SkillProfileDTO.class);
    }

    @GetMapping
    public List<SkillProfileDTO> list() {
        List<SkillProfile> skillProfiles = spS.list();
        return skillProfiles.stream()
                .map(sp -> modelMapper.map(sp, SkillProfileDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping
    public void update(@RequestBody SkillProfileDTO dto) {
        SkillProfile sp = modelMapper.map(dto, SkillProfile.class);
        spS.insert(sp);
    }

    @PutMapping("/profile/{id}")
    public void updateByProfile(@PathVariable("id") Integer profileId, @RequestBody SkillProfileDTO dto) {
        SkillProfile sp = modelMapper.map(dto, SkillProfile.class);

        Profile profile = new Profile();
        profile.setIdProfile(profileId);
        sp.setProfile(profile);

        spS.insert(sp);
    }

    @GetMapping("/user/{idUser}")
    public List<SkillProfileDTO> listByUser(@PathVariable("idUser") int idUser) {
        Optional<User> userOpt = uR.findById(idUser);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUser);
        }

        Profile profile = userOpt.get().getProfile();
        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado.");
        }

        List<SkillProfile> skillProfiles = spS.findByProfile(profile);

        return skillProfiles.stream()
                .map(sp -> {
                    SkillProfileDTO dto = new SkillProfileDTO();
                    dto.setIdSkillProfile(sp.getIdSkillProfile());
                    dto.setIdProfile(sp.getProfile().getIdProfile());
                    dto.setIdSkill(sp.getSkill().getIdSkill());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
