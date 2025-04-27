package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name="Availability")
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
    private String hourStart;

    @Column(name = "hourEnd", nullable = false, length = 5)
    private String hourEnd;

    public Availability() {
    }

    public Availability(int idAvailability, Profile profile, String day, String hourStart, String hourEnd) {
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
