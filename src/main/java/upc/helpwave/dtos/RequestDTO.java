package upc.helpwave.dtos;

import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Skill;

import java.time.LocalDateTime;

public class RequestDTO {
    private int idRequest;
    private int idProfile;
    private int idSkill;
    private Boolean stateRequest;

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public Boolean getStateRequest() {
        return stateRequest;
    }

    public void setStateRequest(Boolean stateRequest) {
        this.stateRequest = stateRequest;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public int getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }
}
