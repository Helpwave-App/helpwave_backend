package upc.helpwave.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRequest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProfile", nullable = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idSkill", nullable = false)
    private Skill skill;

    @Column(name = "dateRequest", nullable = false)
    private LocalDateTime dateRequest;

    @Column(name = "stateRequest", nullable = false)
    private Boolean stateRequest;

    @Column(name = "tokenDevice")
    private String tokenDevice;

    public Request() {
    }

    public Request(int idRequest, Profile profile, Skill skill, LocalDateTime dateRequest, Boolean stateRequest, String tokenDevice) {
        this.idRequest = idRequest;
        this.profile = profile;
        this.skill = skill;
        this.dateRequest = dateRequest;
        this.stateRequest = stateRequest;
        this.tokenDevice = tokenDevice;
    }

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
