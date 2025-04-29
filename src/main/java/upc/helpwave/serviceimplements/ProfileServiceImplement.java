package upc.helpwave.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.helpwave.entities.Profile;
import upc.helpwave.repositories.ProfileRepository;
import upc.helpwave.serviceinterfaces.IProfileService;

import java.util.List;

@Service
public class ProfileServiceImplement implements IProfileService {
    @Autowired
    private ProfileRepository pR;

    @Override
    public void insert(Profile profile) {
        pR.save(profile);
    }

    @Override
    public void delete(Integer idProfile) {
        pR.deleteById(idProfile);
    }

    @Override
    public Profile listId(Integer idProfile) {
        return pR.findById(idProfile).orElse(new Profile());
    }

    @Override
    public List<Profile> list() {
        return pR.findAll();
    }
}
