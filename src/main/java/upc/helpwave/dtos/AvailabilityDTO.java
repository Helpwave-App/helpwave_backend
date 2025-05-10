package upc.helpwave.dtos;

import java.time.LocalTime;

import upc.helpwave.entities.Profile;

import java.time.LocalTime;

public class AvailabilityDTO {
    private int idAvailability;
    private int idProfile;
    private String day;
    private LocalTime hourStart;
    private LocalTime hourEnd;

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

    public LocalTime getHourStart() {
        return hourStart;
    }

    public void setHourStart(LocalTime hourStart) {
        this.hourStart = hourStart;
    }

    public LocalTime getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(LocalTime hourEnd) {
        this.hourEnd = hourEnd;
    }
}
