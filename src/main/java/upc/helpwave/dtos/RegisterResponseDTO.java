package upc.helpwave.dtos;

public class RegisterResponseDTO {
    private int idUser;
    private int idProfile;
    private String message;

    public RegisterResponseDTO(int idUser, int idProfile, String message) {
        this.idUser = idUser;
        this.idProfile = idProfile;
        this.message = message;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
