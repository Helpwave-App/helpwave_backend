package upc.helpwave.dtos;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Request;

public class EmpairingDTO {
    private int idEmpairing;
    private Request request;
    private Profile profile;
    private Boolean stateEmpairing;

    public int getIdEmpairing() {
        return idEmpairing;
    }

    public void setIdEmpairing(int idEmpairing) {
        this.idEmpairing = idEmpairing;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Boolean getStateEmpairing() {
        return stateEmpairing;
    }

    public void setStateEmpairing(Boolean stateEmpairing) {
        this.stateEmpairing = stateEmpairing;
    }
}
