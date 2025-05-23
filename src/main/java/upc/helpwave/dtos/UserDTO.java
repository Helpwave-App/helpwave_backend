package upc.helpwave.dtos;
import upc.helpwave.entities.Profile;
import upc.helpwave.entities.Role;

import java.util.List;

public class UserDTO {
    private Integer idUser;
    private Profile profile;
    private String username;
    private String password;
    private Boolean state;
    private Long idRole;


    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long role) {
        this.idRole = role;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
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
}
