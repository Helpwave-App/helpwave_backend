package upc.helpwave.dtos;

import upc.helpwave.entities.Level;

import java.time.LocalDate;

public class ProfileDTO {
    private int idProfile;
    private Level level;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private Double scoreProfile;

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getScoreProfile() {
        return scoreProfile;
    }

    public void setScoreProfile(Double scoreProfile) {
        this.scoreProfile = scoreProfile;
    }
}
