package upc.helpwave.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Level")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLevel;

    @Column(name = "nameLevel", nullable = false, length = 35)
    private String nameLevel;

    @Column(name = "minRequest", nullable = false)
    private int minRequest;

    @Column(name = "maxRequest", nullable = false)
    private int maxRequest;

    @Column(name = "photoUrl", length = 500)
    private String photoUrl;

    public Level() {
    }

    public Level(int idLevel, String nameLevel, int minRequest, int maxRequest, String photoUrl) {
        this.idLevel = idLevel;
        this.nameLevel = nameLevel;
        this.minRequest = minRequest;
        this.maxRequest = maxRequest;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getNameLevel() {
        return nameLevel;
    }

    public void setNameLevel(String nameLevel) {
        this.nameLevel = nameLevel;
    }

    public int getMinRequest() {
        return minRequest;
    }

    public void setMinRequest(int minRequest) {
        this.minRequest = minRequest;
    }

    public int getMaxRequest() {
        return maxRequest;
    }

    public void setMaxRequest(int maxRequest) {
        this.maxRequest = maxRequest;
    }
}
