package upc.helpwave.dtos;

import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Skill;

public class TaskProfileDTO {
    private int idSkillProfile;
    private Profile profile;
    private Skill skill;

    public int getIdSkillProfile() {
        return idSkillProfile;
    }

    public void setIdSkillProfile(int idSkillProfile) {
        this.idSkillProfile = idSkillProfile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
