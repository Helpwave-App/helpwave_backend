package upc.helpwave.dtos;

import java.util.List;

public class LanguageProfileListDTO {
    private int idProfile;
    private List<Integer> languageIds;

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public List<Integer> getLanguageIds() {
        return languageIds;
    }

    public void setLanguageIds(List<Integer> skillIds) {
        this.languageIds = skillIds;
    }
}
