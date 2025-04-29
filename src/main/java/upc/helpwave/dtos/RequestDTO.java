package upc.helpwave.dtos;

import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Skill;

import java.time.LocalDateTime;

public class RequestDTO {
    private int idRequest;
    private Profile profile;
    private Skill skill;
    private LocalDateTime dateRequest;
    private Boolean stateRequest;

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
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

    public LocalDateTime getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(LocalDateTime dateRequest) {
        this.dateRequest = dateRequest;
    }

    public Boolean getStateRequest() {
        return stateRequest;
    }

    public void setStateRequest(Boolean stateRequest) {
        this.stateRequest = stateRequest;
    }
}
