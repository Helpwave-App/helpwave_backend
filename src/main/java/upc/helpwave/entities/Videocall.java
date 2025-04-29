package upc.helpwave.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Videocall")
public class Videocall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVideocall;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEmpairing", nullable = false)
    private Empairing empairing;

    @Column(name = "token", nullable = false, length = 300)
    private String token;

    @Column(name = "channel", nullable = false, length = 300)
    private String channel;

    @Column(name = "startVideocall", nullable = false)
    private LocalDateTime startVideocall;

    @Column(name = "endVideocall", nullable = false)
    private LocalDateTime endVideocall;

    public Videocall() {
    }

    public Videocall(int idVideocall, Empairing empairing, String token, String channel, LocalDateTime startVideocall,
            LocalDateTime endVideocall) {
        this.idVideocall = idVideocall;
        this.empairing = empairing;
        this.token = token;
        this.channel = channel;
        this.startVideocall = startVideocall;
        this.endVideocall = endVideocall;
    }

    public int getIdVideocall() {
        return idVideocall;
    }

    public void setIdVideocall(int idVideocall) {
        this.idVideocall = idVideocall;
    }

    public Empairing getEmpairing() {
        return empairing;
    }

    public void setEmpairing(Empairing empairing) {
        this.empairing = empairing;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public LocalDateTime getStartVideocall() {
        return startVideocall;
    }

    public void setStartVideocall(LocalDateTime startVideocall) {
        this.startVideocall = startVideocall;
    }

    public LocalDateTime getEndVideocall() {
        return endVideocall;
    }

    public void setEndVideocall(LocalDateTime endVideocall) {
        this.endVideocall = endVideocall;
    }
}
