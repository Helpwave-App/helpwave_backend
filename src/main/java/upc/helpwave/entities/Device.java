package upc.helpwave.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDevice;

    @Column(name = "tokenDevice", nullable = false, unique = true, length = 255)
    private String tokenDevice;

    @Column(name = "registrationDate", nullable = false)
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    @JsonIgnore
    private User user;

    public Device() {}

    public Device(int idDevice, String tokenDevice, LocalDateTime registrationDate, User user) {
        this.idDevice = idDevice;
        this.tokenDevice = tokenDevice;
        this.registrationDate = registrationDate;
        this.user = user;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
