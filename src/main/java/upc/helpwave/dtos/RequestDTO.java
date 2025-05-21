package upc.helpwave.dtos;

public class RequestDTO {
    private int idRequest;
    private int idProfile;
    private int idSkill;
    private Boolean stateRequest;
    private String tokenDevice;

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
}
