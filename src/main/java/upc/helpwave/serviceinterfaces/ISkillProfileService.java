package upc.helpwave.serviceinterfaces;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.SkillProfile;

import java.util.List;

public interface ISkillProfileService {
    public void insert(SkillProfile skillProfile);

    public void delete(Integer idSkillProfile);

    public SkillProfile listId(Integer idSkillProfile);

    public List<SkillProfile> list();

    void insertAll(List<SkillProfile> skillProfiles);

    List<SkillProfile> findByProfile(Profile profile);

}
