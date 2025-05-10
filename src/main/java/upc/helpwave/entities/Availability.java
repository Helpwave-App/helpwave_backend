package upc.helpwave.entities;

import java.time.LocalTime;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "Availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAvailability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProfile", nullable = false)
    private Profile profile;

    @Column(name = "day", nullable = false, length = 1)
    private String day;

    @Column(name = "hourStart", nullable = false, length = 5)
    private LocalTime hourStart;

    @Column(name = "hourEnd", nullable = false, length = 5)
    private LocalTime hourEnd;

    public Availability() {
    }

    public Availability(int idAvailability, Profile profile, String day, LocalTime hourStart, LocalTime hourEnd) {
        this.idAvailability = idAvailability;
        this.profile = profile;
        this.day = day;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
    }

    public int getIdAvailability() {
        return idAvailability;
    }

    public void setIdAvailability(int idAvailability) {
        this.idAvailability = idAvailability;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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
