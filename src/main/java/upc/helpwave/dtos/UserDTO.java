package upc.helpwave.dtos;

public class UserDTO {
    private int idUser;
    private String username;
    private String password;
    private Boolean state;
    private Long idRole;
    private ProfileDTO profileDto;

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long role) {
        this.idRole = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public ProfileDTO getProfile() {
        return profileDto;
    }

    public void setProfile(ProfileDTO profile) {
        this.profileDto = profile;
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
