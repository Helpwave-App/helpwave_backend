package upc.helpwave.dtos;

import java.util.List;

public class SkillProfileListDTO {
    private int idProfile;
    private List<Integer> skillIds;

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public List<Integer> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(List<Integer> skillIds) {
        this.skillIds = skillIds;
    }
}
