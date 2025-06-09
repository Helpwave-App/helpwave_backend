package upc.helpwave.serviceinterfaces;

import upc.helpwave.entities.LanguageProfile;
import upc.helpwave.entities.Profile;
import java.util.List;

public interface ILanguageProfileService {
    public void insert(LanguageProfile languageProfile);

    public void delete(Integer idLanguageProfile);

    public LanguageProfile listId(Integer idLanguageProfile);

    public List<LanguageProfile> list();

    void insertAll(List<LanguageProfile> languageProfiles);

    List<LanguageProfile> findByProfile(Profile profile);
}
