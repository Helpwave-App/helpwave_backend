package upc.helpwave.dtos;

import java.time.LocalDateTime;

public class RequestDTO {
    private int idRequest;
    private int idProfile;
    private int idSkill;
    private Boolean stateRequest;
    private String tokenDevice;
    private LocalDateTime dateRequest;
    private String duration;
    private String skillDescription;

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }

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

    public LocalDateTime getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(LocalDateTime dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }
}
