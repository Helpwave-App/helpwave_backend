package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "SkillProfile")
public class SkillProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSkillProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProfile", nullable = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idSkill", nullable = false)
    private Skill skill;

    public SkillProfile(int idSkillProfile, Profile profile, Skill skill) {
        this.idSkillProfile = idSkillProfile;
        this.profile = profile;
        this.skill = skill;
    }

    public SkillProfile() {
    }

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
