package upc.helpwave.dtos;

import upc.helpwave.entities.Profile;

public class AvailabilityDTO {
    private int idAvailability;
    private int idProfile;
    private String day;
    private String hourStart;
    private String hourEnd;

    public int getIdAvailability() {
        return idAvailability;
    }

    public void setIdAvailability(int idAvailability) {
        this.idAvailability = idAvailability;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

    public String getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }
}
