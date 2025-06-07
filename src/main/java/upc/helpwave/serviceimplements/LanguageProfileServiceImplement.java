package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.LanguageProfile;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.LanguageProfileRepository;
import upc.helpwave.serviceinterfaces.ILanguageProfileService;

import java.util.List;

@Service
public class LanguageProfileServiceImplement implements ILanguageProfileService {
    @Autowired
    private LanguageProfileRepository lR;

    @Override
    public void insert(LanguageProfile language) {
        lR.save(language);
    }
    @Override
    public void delete(Integer idLanguageProfile) {
        lR.deleteById(idLanguageProfile);
    }
    @Override
    public LanguageProfile listId(Integer idLanguageProfile) {
        return lR.findById(idLanguageProfile).orElse(new LanguageProfile());
    }
    @Override
    public List<LanguageProfile> list() {
        return lR.findAll();
    }
    @Override
    public List<LanguageProfile> findByProfile(Profile profile) {
        return lR.findByProfile(profile);
    }
}
