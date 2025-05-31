package upc.helpwave.dtos;

import upc.helpwave.entities.Level;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProfileDTO {
    private int idProfile;
    private Level level;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private BigDecimal scoreProfile;
    private String email;
    private String phoneNumber;
    private String photoUrl;
    private Integer assistances;

    public Integer getAssistances() {
        return assistances;
    }

    public void setAssistances(Integer assistances) {
        this.assistances = assistances;
    }

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

    public BigDecimal getScoreProfile() {
        return scoreProfile;
    }

    public void setScoreProfile(BigDecimal scoreProfile) {
        this.scoreProfile = scoreProfile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
