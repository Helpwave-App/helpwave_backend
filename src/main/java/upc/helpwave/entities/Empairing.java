package upc.helpwave.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Empairing")
public class Empairing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpairing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRequest", nullable = false)
    private Request request;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProfile", nullable = false)
    private Profile profile;

    @Column(name = "stateEmpairing", nullable = false)
    private Boolean stateEmpairing;

    @Column(name = "dateEmpairing", nullable = true)
    private LocalDateTime dateEmpairing;

    public Empairing() {
    }

    public Empairing(int idEmpairing, Request request, Profile profile, Boolean stateEmpairing, LocalDateTime dateEmpairing) {
        this.idEmpairing = idEmpairing;
        this.request = request;
        this.profile = profile;
        this.stateEmpairing = stateEmpairing;
        this.dateEmpairing = dateEmpairing;
    }

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

    public LocalDateTime getDateEmpairing() {
        return dateEmpairing;
    }

    public void setDateEmpairing(LocalDateTime dateEmpairing) {
        this.dateEmpairing = dateEmpairing;
    }
}
