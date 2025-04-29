package upc.helpwave.dtos;

import upc.helpwave.entities.Language;
import upc.helpwave.entities.Profile;

public class LanguageProfileDTO {
    private int idLanguageProfile;
    private Language language;
    private Profile profile;

    public int getIdLanguageProfile() {
        return idLanguageProfile;
    }

    public void setIdLanguageProfile(int idLanguageProfile) {
        this.idLanguageProfile = idLanguageProfile;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
