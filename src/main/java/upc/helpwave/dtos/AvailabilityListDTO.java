package upc.helpwave.dtos;

import java.util.List;

public class AvailabilityListDTO {
    private int idProfile;
    private List<AvailabilityDTO> availabilities;

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }
}
