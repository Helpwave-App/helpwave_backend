package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.SkillProfile;
import upc.helpwave.repositories.SkillProfileRepository;
import upc.helpwave.serviceinterfaces.ISkillProfileService;

import java.util.List;

@Service
public class SkillProfileServiceImplement implements ISkillProfileService {
    @Autowired
    private SkillProfileRepository spR;

    public SkillProfileServiceImplement(SkillProfileRepository spR) {
        this.spR = spR;
    }

    @Override
    public void insert(SkillProfile skillProfile) {
        spR.save(skillProfile);
    }

    @Override
    public void delete(Integer idSkillProfile) {
        spR.deleteById(idSkillProfile);
    }

    @Override
    public SkillProfile listId(Integer idSkillProfile) {
        return spR.findById(idSkillProfile).orElse(new SkillProfile());
    }

    @Override
    public List<SkillProfile> list() {
        return spR.findAll();
    }

    @Override
    public void insertAll(List<SkillProfile> skillProfiles) {
        spR.saveAll(skillProfiles);
    }

    @Override
    public List<SkillProfile> findByProfile(Profile profile) {
        return spR.findByProfile(profile);
    }

}
