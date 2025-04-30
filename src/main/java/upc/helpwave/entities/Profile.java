package upc.helpwave.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idLevel")
    private Level level;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "lastName", nullable = false, length = 30)
    private String lastName;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Column(name = "scoreProfile")
    private Double scoreProfile;

    public Profile() {
    }

    public Profile(int idProfile, String name, String lastName, Double scoreProfile) {
        this.idProfile = idProfile;
        this.name = name;
        this.lastName = lastName;
        this.scoreProfile = scoreProfile;
    }

    public Profile(int idProfile, Level level, String name, String lastName, LocalDate birthDate, Double scoreProfile) {
        this.idProfile = idProfile;
        this.level = level;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.scoreProfile = scoreProfile;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getScoreProfile() {
        return scoreProfile;
    }

    public void setScoreProfile(Double scoreProfile) {
        this.scoreProfile = scoreProfile;
    }
}
