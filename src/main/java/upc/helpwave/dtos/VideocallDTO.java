package upc.helpwave.dtos;

import upc.helpwave.entities.Empairing;
import java.time.LocalDateTime;

public class VideocallDTO {
    //private int idVideocall;
    //private Empairing empairing;
    private String token;
    private String channel;
    //private LocalDateTime startVideocall;
    //private LocalDateTime endVideocall;

    /*public int getIdVideocall() {
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
    */
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
    /*
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
    }*/

    public VideocallDTO(String token, String channel) {
        this.token = token;
        this.channel = channel;
    }
    public VideocallDTO() {}
}
