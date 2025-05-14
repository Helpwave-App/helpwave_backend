package upc.helpwave.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idProfile", referencedColumnName = "idProfile")
    private Profile profile;

    @Column(name = "username",nullable = false, length = 30, unique = true)
    private String username;

    @Column(name = "password",nullable = false, length = 200)
    private String password;

    @Column(name = "state", nullable = false, length = 1)
    private Boolean state;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices = new ArrayList<>();

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public User(int idUser, Profile profile, String username, String password, Boolean state, Role role) {
        this.idUser = idUser;
        this.profile = profile;
        this.username = username;
        this.password = password;
        this.state = state;
        this.role = role;
    }

    public User() {
    }
}
