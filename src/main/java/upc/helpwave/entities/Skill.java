package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSkill;

    @Column(name = "skillDesc", nullable = false, length = 50)
    private String skillDesc;

    public int getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }

    public String getSkillDesc() {
        return skillDesc;
    }

    public void setSkillDesc(String skillDesc) {
        this.skillDesc = skillDesc;
    }

    public Skill(int idSkill, String skillDesc) {
        this.idSkill = idSkill;
        this.skillDesc = skillDesc;
    }

    public Skill() {
    }
}
